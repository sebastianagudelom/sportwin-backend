package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.EventoDeportivo;
import java.time.LocalDate;
import java.util.List;

public interface EventoDeportivoService {

    // Obtener todos los eventos deportivos
    List<EventoDeportivo> obtenerTodosLosEventosDeportivos();

    // Obtener un evento deportivo por su ID
    EventoDeportivo obtenerEventoDeportivoPorId(Long id);

    // Crear un nuevo evento deportivo
    EventoDeportivo crearEventoDeportivo(EventoDeportivo eventoDeportivo);

    // Actualizar un evento deportivo existente
    EventoDeportivo actualizarEventoDeportivo(EventoDeportivo eventoDeportivo);

    // Eliminar un evento deportivo
    void eliminarEventoDeportivo(Long id);

    // Obtener eventos por competición
    List<EventoDeportivo> obtenerEventosPorCompeticion(Long competicionId);

    // Obtener eventos por ID de equipo
    List<EventoDeportivo> obtenerEventosPorEquipo(Long equipoId);

    // Obtener eventos por fecha
    List<EventoDeportivo> obtenerEventosPorFecha(LocalDate fecha);

    // Obtener eventos activos
    List<EventoDeportivo> obtenerEventosActivos();

    // Finalizar un evento deportivo
    EventoDeportivo finalizarEvento(Long id, Integer resultadoLocal, Integer resultadoVisitante);

    // Suspender un evento deportivo
    EventoDeportivo suspenderEvento(Long id);

    // Verificar existencia por ID
    boolean existeEventoPorId(Long id);
}
