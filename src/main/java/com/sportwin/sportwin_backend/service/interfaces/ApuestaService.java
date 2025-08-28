package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.Apuesta;
import java.util.List;

public interface ApuestaService {
    
    // Crear una nueva apuesta
    Apuesta crearApuesta(Apuesta apuesta);
    
    // Obtener una apuesta por su ID
    Apuesta obtenerApuestaPorId(Long id);
    
    // Obtener todas las apuestas
    List<Apuesta> obtenerTodasLasApuestas();
    
    // Obtener apuestas por usuario
    List<Apuesta> obtenerApuestasPorUsuario(Long idUsuario);
    
    // Actualizar estado de una apuesta
    Apuesta actualizarEstadoApuesta(Long id, String nuevoEstado);
    
    // Actualizar una apuesta
    Apuesta actualizarApuesta(Apuesta apuesta);
    
    // Eliminar una apuesta
    void eliminarApuesta(Long id);
}
