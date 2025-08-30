package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.TipoApuesta;
import java.util.List;

public interface TipoApuestaService {

    // Obtener todos los tipos de apuesta
    List<TipoApuesta> obtenerTodosLosTiposApuesta();

    // Obtener un tipo de apuesta por su ID
    TipoApuesta obtenerTipoApuestaPorId(Long id);

    // Crear un nuevo tipo de apuesta
    TipoApuesta crearTipoApuesta(TipoApuesta tipoApuesta);

    // Actualizar un tipo de apuesta existente
    TipoApuesta actualizarTipoApuesta(Long id, TipoApuesta tipoApuesta);

    // Eliminar un tipo de apuesta existente
    void eliminarTipoApuesta(Long id);
}
