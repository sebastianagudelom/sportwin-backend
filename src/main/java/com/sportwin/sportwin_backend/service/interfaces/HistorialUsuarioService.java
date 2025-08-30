package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.HistorialUsuario;
import java.time.LocalDateTime;
import java.util.List;


public interface HistorialUsuarioService {

    // Obtener todos los historiales
    List<HistorialUsuario> obtenerTodosLosHistoriales();

    // Obtener un historial por su ID
    HistorialUsuario obtenerHistorialPorId(Long id);

    // Obtener historial por ID de usuario
    HistorialUsuario obtenerHistorialPorUsuario(Long idUsuario);

    // Crear un nuevo historial
    HistorialUsuario crearHistorial(HistorialUsuario historialUsuario);

    // Actualizar un historial existente
    HistorialUsuario actualizarHistorial(Long id, HistorialUsuario historialUsuario);

    // Eliminar un historial
    void eliminarHistorial(Long id);

    // Obtener historiales por rango de fechas
    List<HistorialUsuario> obtenerHistorialesPorRango(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Obtener historiales por tipo de operación
    List<HistorialUsuario> obtenerHistorialesPorTipo(String tipoOperacion);

    // Verificar existencia por ID
    boolean existeHistorialPorId(Long id);

    // Verificar existencia por ID de usuario
    boolean existeHistorialPorUsuario(Long idUsuario);
}
