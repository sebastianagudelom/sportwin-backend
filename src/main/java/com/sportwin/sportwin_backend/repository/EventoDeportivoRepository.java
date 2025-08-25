package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.EventoDeportivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoDeportivoRepository extends JpaRepository<EventoDeportivo, Long> {
}
