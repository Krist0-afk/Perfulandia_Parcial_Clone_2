package com.perfulandia.usuarioservice.controller;

import com.perfulandia.usuarioservice.assembler.UsuarioAssembler;
import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.service.UsuarioService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioAssembler assembler;

    public UsuarioController(UsuarioService service, UsuarioAssembler assembler){
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/listar/usuario")
    public CollectionModel<EntityModel<Usuario>> listar(){
        List<EntityModel<Usuario>> usuarios = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(UsuarioController.class).slash("/listar/usuario").withSelfRel());
    }

    @PostMapping("/guardar/usuario")
    public EntityModel<Usuario> guardar(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = service.guardar(usuario);
        return assembler.toModel(nuevoUsuario);
    }

    @GetMapping("/buscar/usuario/{id}")
    public EntityModel<Usuario> buscar(@PathVariable long id){
        Usuario usuario = service.buscar(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con id " + id);
        }
        return assembler.toModel(usuario);
    }

    @DeleteMapping("/eliminar/usuario/{id}")
    public void eliminar(@PathVariable long id){
        service.eliminar(id);
    }
}
