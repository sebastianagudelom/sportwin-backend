package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.Equipo;
import java.util.List;

public interface EquipoService {

    // Obtener todos los equipos
    List<Equipo> obtenerTodosLosEquipos();

    // Obtener un equipo por su ID
    Equipo obtenerEquipoPorId(Long id);

    // Crear un nuevo equipo
    Equipo crearEquipo(Equipo equipo);

    // Actualizar un equipo existente
    Equipo actualizarEquipo(Equipo equipo);

    // Eliminar un equipo
    void eliminarEquipo(Long id);

    // Verificar existencia por ID
    boolean existeEquipoPorId(Long id);

    // Verificar existencia por nombre
    boolean existeEquipoPorNombre(String nombre);

    // Obtener todos los equipos de un deporte específico
    List<Equipo> obtenerEquiposPorDeporte(Long deporteId);
}
