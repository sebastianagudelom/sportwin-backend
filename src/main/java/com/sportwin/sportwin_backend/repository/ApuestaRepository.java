package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.Apuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApuestaRepository extends JpaRepository<Apuesta, Long> {
    List<Apuesta> findByUsuarioIdUsuario(Long idUsuario);
}
