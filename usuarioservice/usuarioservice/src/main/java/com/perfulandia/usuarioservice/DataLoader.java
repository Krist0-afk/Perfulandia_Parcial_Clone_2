package com.perfulandia.usuarioservice;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private final UsuarioRepository repository;

    public DataLoader(UsuarioRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void loadData() {
        repository.save(new Usuario(0, "Luis Uribe", "luis@example.com", "ADMIN"));
        repository.save(new Usuario(0, "Ana Perez", "ana@example.com", "GERENTE"));
        repository.save(new Usuario(0, "Carlos Soto", "carlos@example.com", "Usuario"));
    }
}
