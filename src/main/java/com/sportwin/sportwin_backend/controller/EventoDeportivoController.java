package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.EventoDeportivo;
import com.sportwin.sportwin_backend.service.interfaces.EventoDeportivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/eventos-deportivos")
@CrossOrigin(origins = "*")
public class EventoDeportivoController {

    @Autowired
    private EventoDeportivoService eventoDeportivoService;

    // Listar todos los eventos deportivos
    @GetMapping
    public ResponseEntity<List<EventoDeportivo>> getAllEventosDeportivos() {
        try {
            List<EventoDeportivo> eventos = eventoDeportivoService.obtenerTodosLosEventosDeportivos();
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar evento deportivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEventoDeportivoById(@PathVariable Long id) {
        try {
            EventoDeportivo evento = eventoDeportivoService.obtenerEventoDeportivoPorId(id);
            return ResponseEntity.ok(evento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener el evento deportivo: " + e.getMessage());
        }
    }

    // Obtener eventos por competición
    @GetMapping("/competicion/{competicionId}")
    public ResponseEntity<?> getEventosPorCompeticion(@PathVariable Long competicionId) {
        try {
            List<EventoDeportivo> eventos = eventoDeportivoService.obtenerEventosPorCompeticion(competicionId);
            return ResponseEntity.ok(eventos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener los eventos: " + e.getMessage());
        }
    }

    // Obtener eventos por equipo
    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<?> getEventosPorEquipo(@PathVariable Long equipoId) {
        try {
            List<EventoDeportivo> eventos = eventoDeportivoService.obtenerEventosPorEquipo(equipoId);
            return ResponseEntity.ok(eventos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener los eventos: " + e.getMessage());
        }
    }

    // Obtener eventos por fecha
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<?> getEventosPorFecha(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
        try {
            List<EventoDeportivo> eventos = eventoDeportivoService.obtenerEventosPorFecha(fecha);
            return ResponseEntity.ok(eventos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener los eventos: " + e.getMessage());
        }
    }

    // Obtener eventos activos
    @GetMapping("/activos")
    public ResponseEntity<?> getEventosActivos() {
        try {
            List<EventoDeportivo> eventos = eventoDeportivoService.obtenerEventosActivos();
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener los eventos activos: " + e.getMessage());
        }
    }

    // Crear evento deportivo
    @PostMapping
    public ResponseEntity<?> createEventoDeportivo(@RequestBody EventoDeportivo eventoDeportivo) {
        try {
            EventoDeportivo nuevoEvento = eventoDeportivoService.crearEventoDeportivo(eventoDeportivo);
            return ResponseEntity.ok(nuevoEvento);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear el evento deportivo: " + e.getMessage());
        }
    }

    // Actualizar evento deportivo
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEventoDeportivo(@PathVariable Long id, 
            @RequestBody EventoDeportivo eventoDeportivo) {
        try {
            eventoDeportivo.setIdEvento(id);
            EventoDeportivo eventoActualizado = eventoDeportivoService.actualizarEventoDeportivo(eventoDeportivo);
            return ResponseEntity.ok(eventoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al actualizar el evento deportivo: " + e.getMessage());
        }
    }

    // Finalizar evento
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarEvento(@PathVariable Long id, 
            @RequestParam Integer resultadoLocal, 
            @RequestParam Integer resultadoVisitante) {
        try {
            EventoDeportivo eventoFinalizado = eventoDeportivoService.finalizarEvento(id, resultadoLocal, resultadoVisitante);
            return ResponseEntity.ok(eventoFinalizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al finalizar el evento: " + e.getMessage());
        }
    }

    // Suspender evento
    @PutMapping("/{id}/suspender")
    public ResponseEntity<?> suspenderEvento(@PathVariable Long id) {
        try {
            EventoDeportivo eventoSuspendido = eventoDeportivoService.suspenderEvento(id);
            return ResponseEntity.ok(eventoSuspendido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al suspender el evento: " + e.getMessage());
        }
    }

    // Eliminar evento deportivo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventoDeportivo(@PathVariable Long id) {
        try {
            eventoDeportivoService.eliminarEventoDeportivo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al eliminar el evento deportivo: " + e.getMessage());
        }
    }
}
