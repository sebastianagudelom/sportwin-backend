package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.HistorialUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HistorialUsuarioRepository extends JpaRepository<HistorialUsuario, Integer> {
    Optional<HistorialUsuario> findByUsuario_IdUsuario(Integer idUsuario);
}
