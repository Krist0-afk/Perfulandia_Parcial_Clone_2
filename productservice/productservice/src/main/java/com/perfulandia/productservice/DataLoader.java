package com.perfulandia.productservice;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository repo;

    public DataLoader(ProductoRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        repo.save(new Producto(0, "Collar para perro", 10000, 20));
        repo.save(new Producto(0, "Rascador para gato", 15000, 10));
        repo.save(new Producto(0, "Pecera pequeña", 20000, 5));
    }
}
