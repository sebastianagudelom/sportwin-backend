package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    // Método para buscar transacciones por usuario
    List<Transaccion> findByUsuarioIdUsuario(Long idUsuario);
    
    // Método para buscar transacciones por tipo
    List<Transaccion> findByTipo(String tipo);
    
    // Método para buscar transacciones por estado
    List<Transaccion> findByEstadoTransaccion(String estado);
    
    // Método para buscar transacciones por usuario y tipo
    List<Transaccion> findByUsuarioIdUsuarioAndTipo(Long idUsuario, String tipo);
    
    // Método para buscar transacciones por usuario y estado
    List<Transaccion> findByUsuarioIdUsuarioAndEstadoTransaccion(Long idUsuario, String estado);
}
