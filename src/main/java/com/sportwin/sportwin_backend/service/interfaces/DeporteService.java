package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.Deporte;
import java.util.List;

public interface DeporteService {

    // Obtener todos los deportes
    List<Deporte> obtenerTodosLosDeportes();

    // Obtener un deporte por su ID
    Deporte obtenerDeportePorId(Long id);

    // Crear un deporte
    Deporte crearDeporte(Deporte deporte);

    // Actualizar un deporte
    Deporte actualizarDeporte(Deporte deporte);

    // Eliminar un deporte
    void eliminarDeporte(Long id);

    // Verificar existencia por ID
    boolean existeDeportePorId(Long id);

    // Verificar existencia por nombre
    boolean existeDeportePorNombre(String nombreDeporte);
}
