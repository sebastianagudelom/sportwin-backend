package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.EventoDeportivo;
import com.sportwin.sportwin_backend.repository.EventoDeportivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos-deportivos")
@CrossOrigin(origins = "*")
public class EventoDeportivoController {

    @Autowired
    private EventoDeportivoRepository eventoDeportivoRepository;

    // Listar todos los eventos deportivos
    @GetMapping // http://localhost:8080/api/eventos-deportivos
    public List<EventoDeportivo> getAllEventosDeportivos() {
        return eventoDeportivoRepository.findAll();
    }

    // Buscar evento deportivo por ID
    @GetMapping("/{id}") // http://localhost:8080/api/eventos-deportivos/{id}
    public ResponseEntity<EventoDeportivo> getEventoDeportivoById(@PathVariable Long id) {
        return eventoDeportivoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear evento deportivo
    @PostMapping // http://localhost:8080/api/eventos-deportivos
    public EventoDeportivo createEventoDeportivo(@RequestBody EventoDeportivo eventoDeportivo) {
        return eventoDeportivoRepository.save(eventoDeportivo);
    }

    // Actualizar evento deportivo
    @PutMapping("/{id}") // http://localhost:8080/api/eventos-deportivos/{id}
    public EventoDeportivo updateEventoDeportivo(@PathVariable Long id, @RequestBody EventoDeportivo eventoDeportivo) {
        EventoDeportivo eventoExistente = eventoDeportivoRepository.findById(id).orElse(null);
        if (eventoExistente != null) {
            eventoExistente.setIdEvento(id);
            eventoExistente.setCompeticion(eventoDeportivo.getCompeticion());
            eventoExistente.setFechaEvento(eventoDeportivo.getFechaEvento());
            eventoExistente.setEquipoLocal(eventoDeportivo.getEquipoLocal());
            eventoExistente.setEquipoVisitante(eventoDeportivo.getEquipoVisitante());
            eventoExistente.setEstado(eventoDeportivo.getEstado());
            eventoExistente.setHoraEvento(eventoDeportivo.getHoraEvento());
            eventoExistente.setResultadoLocal(eventoDeportivo.getResultadoLocal());
            eventoExistente.setResultadoVisitante(eventoDeportivo.getResultadoVisitante());
            return eventoDeportivoRepository.save(eventoExistente);
        }
        return null;
    }

    // Eliminar evento deportivo
    @DeleteMapping("/{id}") // http://localhost:8080/api/eventos-deportivos/{id}
    public ResponseEntity<Void> deleteEventoDeportivo(@PathVariable Long id) {
        eventoDeportivoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
