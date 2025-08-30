package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.Competicion;
import com.sportwin.sportwin_backend.entity.Equipo;
import com.sportwin.sportwin_backend.entity.EventoDeportivo;
import com.sportwin.sportwin_backend.repository.CompeticionRepository;
import com.sportwin.sportwin_backend.repository.EquipoRepository;
import com.sportwin.sportwin_backend.repository.EventoDeportivoRepository;
import com.sportwin.sportwin_backend.service.interfaces.EventoDeportivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoDeportivoServiceImpl implements EventoDeportivoService {

    @Autowired
    private EventoDeportivoRepository eventoDeportivoRepository;

    @Autowired
    private CompeticionRepository competicionRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public List<EventoDeportivo> obtenerTodosLosEventosDeportivos() {
        return eventoDeportivoRepository.findAll();
    }

    @Override
    public EventoDeportivo obtenerEventoDeportivoPorId(Long id) {
        return eventoDeportivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el evento deportivo con ID: " + id));
    }

    @Override
    public EventoDeportivo crearEventoDeportivo(EventoDeportivo eventoDeportivo) {
        validarEventoDeportivo(eventoDeportivo);
        validarEquiposDiferentes(eventoDeportivo);
        validarFechaEvento(eventoDeportivo);
        validarEquiposEnMismaCompeticion(eventoDeportivo);

        eventoDeportivo.setEstado("PROGRAMADO");
        return eventoDeportivoRepository.save(eventoDeportivo);
    }

    @Override
    public EventoDeportivo actualizarEventoDeportivo(EventoDeportivo eventoDeportivo) {
        if (eventoDeportivo.getIdEvento() == null) {
            throw new RuntimeException("El ID del evento deportivo no puede ser nulo");
        }

        EventoDeportivo eventoExistente = obtenerEventoDeportivoPorId(eventoDeportivo.getIdEvento());
        
        if ("FINALIZADO".equals(eventoExistente.getEstado())) {
            throw new RuntimeException("No se puede actualizar un evento finalizado");
        }

        validarEventoDeportivo(eventoDeportivo);
        validarEquiposDiferentes(eventoDeportivo);
        validarFechaEvento(eventoDeportivo);
        validarEquiposEnMismaCompeticion(eventoDeportivo);

        return eventoDeportivoRepository.save(eventoDeportivo);
    }

    @Override
    public void eliminarEventoDeportivo(Long id) {
        EventoDeportivo evento = obtenerEventoDeportivoPorId(id);
        
        if ("FINALIZADO".equals(evento.getEstado())) {
            throw new RuntimeException("No se puede eliminar un evento finalizado");
        }
        
        eventoDeportivoRepository.deleteById(id);
    }

    @Override
    public List<EventoDeportivo> obtenerEventosPorCompeticion(Long competicionId) {
        if (!competicionRepository.existsById(competicionId)) {
            throw new RuntimeException("No se encontró la competición con ID: " + competicionId);
        }

        return eventoDeportivoRepository.findAll().stream()
                .filter(e -> e.getCompeticion().getIdCompeticion().equals(competicionId))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventoDeportivo> obtenerEventosPorEquipo(Long equipoId) {
        if (!equipoRepository.existsById(equipoId)) {
            throw new RuntimeException("No se encontró el equipo con ID: " + equipoId);
        }

        return eventoDeportivoRepository.findAll().stream()
                .filter(e -> e.getEquipoLocal().getIdEquipo().equals(equipoId) || 
                           e.getEquipoVisitante().getIdEquipo().equals(equipoId))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventoDeportivo> obtenerEventosPorFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new RuntimeException("La fecha no puede ser nula");
        }

        return eventoDeportivoRepository.findAll().stream()
                .filter(e -> e.getFechaEvento().equals(fecha))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventoDeportivo> obtenerEventosActivos() {
        return eventoDeportivoRepository.findAll().stream()
                .filter(e -> "PROGRAMADO".equals(e.getEstado()))
                .filter(e -> e.getFechaEvento().isAfter(LocalDate.now()) || 
                           (e.getFechaEvento().isEqual(LocalDate.now()) && 
                            e.getHoraEvento().isAfter(LocalDateTime.now().toLocalTime())))
                .collect(Collectors.toList());
    }

    @Override
    public EventoDeportivo finalizarEvento(Long id, Integer resultadoLocal, Integer resultadoVisitante) {
        EventoDeportivo evento = obtenerEventoDeportivoPorId(id);

        if (!"PROGRAMADO".equals(evento.getEstado())) {
            throw new RuntimeException("Solo se pueden finalizar eventos en estado PROGRAMADO");
        }

        if (resultadoLocal == null || resultadoVisitante == null) {
            throw new RuntimeException("Los resultados no pueden ser nulos");
        }

        if (resultadoLocal < 0 || resultadoVisitante < 0) {
            throw new RuntimeException("Los resultados no pueden ser negativos");
        }

        evento.setResultadoLocal(resultadoLocal);
        evento.setResultadoVisitante(resultadoVisitante);
        evento.setEstado("FINALIZADO");

        return eventoDeportivoRepository.save(evento);
    }

    @Override
    public EventoDeportivo suspenderEvento(Long id) {
        EventoDeportivo evento = obtenerEventoDeportivoPorId(id);

        if (!"PROGRAMADO".equals(evento.getEstado())) {
            throw new RuntimeException("Solo se pueden suspender eventos en estado PROGRAMADO");
        }

        evento.setEstado("SUSPENDIDO");
        return eventoDeportivoRepository.save(evento);
    }

    @Override
    public boolean existeEventoPorId(Long id) {
        return eventoDeportivoRepository.existsById(id);
    }

    private void validarEventoDeportivo(EventoDeportivo evento) {
        if (evento == null) {
            throw new RuntimeException("El evento deportivo no puede ser nulo");
        }
        if (evento.getCompeticion() == null || evento.getCompeticion().getIdCompeticion() == null) {
            throw new RuntimeException("La competición es requerida");
        }
        if (evento.getEquipoLocal() == null || evento.getEquipoLocal().getIdEquipo() == null) {
            throw new RuntimeException("El equipo local es requerido");
        }
        if (evento.getEquipoVisitante() == null || evento.getEquipoVisitante().getIdEquipo() == null) {
            throw new RuntimeException("El equipo visitante es requerido");
        }
        if (evento.getFechaEvento() == null) {
            throw new RuntimeException("La fecha del evento es requerida");
        }
        if (evento.getHoraEvento() == null) {
            throw new RuntimeException("La hora del evento es requerida");
        }

        // Validar que la competición existe
        if (!competicionRepository.existsById(evento.getCompeticion().getIdCompeticion())) {
            throw new RuntimeException("La competición especificada no existe");
        }

        // Validar que los equipos existen
        if (!equipoRepository.existsById(evento.getEquipoLocal().getIdEquipo())) {
            throw new RuntimeException("El equipo local especificado no existe");
        }
        if (!equipoRepository.existsById(evento.getEquipoVisitante().getIdEquipo())) {
            throw new RuntimeException("El equipo visitante especificado no existe");
        }
    }

    private void validarEquiposDiferentes(EventoDeportivo evento) {
        if (evento.getEquipoLocal().getIdEquipo().equals(evento.getEquipoVisitante().getIdEquipo())) {
            throw new RuntimeException("El equipo local y visitante no pueden ser el mismo");
        }
    }

    private void validarFechaEvento(EventoDeportivo evento) {
        LocalDateTime fechaHoraEvento = LocalDateTime.of(evento.getFechaEvento(), evento.getHoraEvento());
        if (fechaHoraEvento.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La fecha y hora del evento no puede ser anterior a la actual");
        }
    }

    private void validarEquiposEnMismaCompeticion(EventoDeportivo evento) {
        Competicion competicion = competicionRepository.findById(evento.getCompeticion().getIdCompeticion()).get();
        Equipo equipoLocal = equipoRepository.findById(evento.getEquipoLocal().getIdEquipo()).get();
        Equipo equipoVisitante = equipoRepository.findById(evento.getEquipoVisitante().getIdEquipo()).get();

        if (!equipoLocal.getDeporte().getIdDeporte().equals(competicion.getDeporte().getIdDeporte())) {
            throw new RuntimeException("El equipo local no pertenece al deporte de la competición");
        }
        if (!equipoVisitante.getDeporte().getIdDeporte().equals(competicion.getDeporte().getIdDeporte())) {
            throw new RuntimeException("El equipo visitante no pertenece al deporte de la competición");
        }
    }
}
