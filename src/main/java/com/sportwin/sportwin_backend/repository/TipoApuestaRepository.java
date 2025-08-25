package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.TipoApuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoApuestaRepository extends JpaRepository<TipoApuesta, Long> {
}
