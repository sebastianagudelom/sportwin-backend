package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método personalizado para buscar usuario por correo
    Usuario findByCorreo(String correo);
}