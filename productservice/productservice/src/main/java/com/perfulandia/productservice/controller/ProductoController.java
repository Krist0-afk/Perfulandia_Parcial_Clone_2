package com.perfulandia.productservice.controller;

import com.perfulandia.productservice.Assembler.ProductoAssembler;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.service.ProductoService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService servicio;
    private final ProductoAssembler assembler;
    private final RestTemplate restTemplate;

    public ProductoController(ProductoService servicio, ProductoAssembler assembler, RestTemplate restTemplate) {
        this.servicio = servicio;
        this.assembler = assembler;
        this.restTemplate = restTemplate;
    }

    // listar con HATEOAS
    @GetMapping("/listar/producto")
    public CollectionModel<EntityModel<Producto>> listar() {
        List<EntityModel<Producto>> productos = servicio.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listar()).withSelfRel());
    }

    // guardar (sin HATEOAS)
    @PostMapping("/guardar/producto")
    public Producto guardar(@RequestBody Producto producto) {
        return servicio.guardar(producto);
    }

    // buscar con HATEOAS
    @GetMapping("/buscar/producto{id}")
    public EntityModel<Producto> buscar(@PathVariable long id) {
        Producto producto = servicio.bucarPorId(id);
        return assembler.toModel(producto);
    }

    @DeleteMapping("/eliminar/producto/{id}")
    public void eliminar(@PathVariable long id) {
        servicio.eliminar(id);
    }

    @GetMapping("/usuario/{id}")
    public Usuario obtenerUsuario(@PathVariable long id) {
        return restTemplate.getForObject("http://localhost:8081/api/usuarios/" + id, Usuario.class);
    }
}
