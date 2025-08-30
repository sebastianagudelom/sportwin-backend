package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.Competicion;
import java.util.List;

public interface CompeticionService {
    
    // Obtener todas las competiciones
    List<Competicion> obtenerTodasLasCompeticiones();
    
    // Obtener una competición por su ID
    Competicion obtenerCompeticionPorId(Long id);
    
    // Crear una nueva competición
    Competicion crearCompeticion(Competicion competicion);
    
    // Actualizar una competición
    Competicion actualizarCompeticion(Competicion competicion);
    
    // Eliminar una competición
    void eliminarCompeticion(Long id);
    
    // Obtener competiciones por deporte
    List<Competicion> obtenerCompeticionesPorDeporte(Long idDeporte);
    
    // Obtener competiciones activas
    List<Competicion> obtenerCompeticionesActivas();
}
