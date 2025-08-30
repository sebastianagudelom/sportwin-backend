package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.Transaccion;
import java.util.List;

public interface TransaccionService {

    // Obtener todas las transacciones
    List<Transaccion> obtenerTodasLasTransacciones();

    // Obtener una transacción por su ID
    Transaccion obtenerTransaccionPorId(Long id);

    // Obtener transacciones por usuario
    List<Transaccion> obtenerTransaccionesPorUsuario(Long idUsuario);

    // Obtener transacciones por tipo
    List<Transaccion> obtenerTransaccionesPorTipo(String tipo);

    // Obtener transacciones por estado
    List<Transaccion> obtenerTransaccionesPorEstado(String estado);

    // Crear una nueva transacción
    Transaccion crearTransaccion(Transaccion transaccion);

    // Actualizar una transacción existente
    Transaccion actualizarTransaccion(Long id, Transaccion transaccion);

    // Actualizar el estado de una transacción
    Transaccion actualizarEstadoTransaccion(Long id, String estado);

    // Eliminar una transacción existente
    void eliminarTransaccion(Long id);
}
