package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeporteRepository extends JpaRepository<Deporte, Long> {
}
