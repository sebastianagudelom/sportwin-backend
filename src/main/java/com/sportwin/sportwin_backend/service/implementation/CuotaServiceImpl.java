package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.Cuota;
import com.sportwin.sportwin_backend.entity.EventoDeportivo;
import com.sportwin.sportwin_backend.repository.CuotaRepository;
import com.sportwin.sportwin_backend.repository.EventoDeportivoRepository;
import com.sportwin.sportwin_backend.service.interfaces.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuotaServiceImpl implements CuotaService {

    @Autowired
    private CuotaRepository cuotaRepository;
    
    @Autowired
    private EventoDeportivoRepository eventoRepository;

    @Override
    public List<Cuota> obtenerTodasLasCuotas() {
        return cuotaRepository.findAll();
    }

    @Override
    public Cuota obtenerCuotaPorId(Long id) {
        return cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada con ID: " + id));
    }

    @Override
    public Cuota crearCuota(Cuota cuota) {
        // Validaciones básicas
        if (cuota.getEvento() == null || cuota.getEvento().getIdEvento() == null) {
            throw new RuntimeException("El evento deportivo es requerido");
        }
        if (cuota.getDescripcionCuota() == null || cuota.getDescripcionCuota().trim().isEmpty()) {
            throw new RuntimeException("La descripción de la cuota es requerida");
        }
        if (cuota.getValorCuota() == null || cuota.getValorCuota().compareTo(BigDecimal.ONE) < 0) {
            throw new RuntimeException("El valor de la cuota debe ser mayor o igual a 1");
        }

        // Verificar que el evento existe y está activo
        EventoDeportivo evento = eventoRepository.findById(cuota.getEvento().getIdEvento())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + cuota.getEvento().getIdEvento()));
        
        if (!"activo".equals(evento.getEstado())) {
            throw new RuntimeException("No se pueden crear cuotas para eventos no activos");
        }

        // Establecer valores por defecto
        cuota.setEvento(evento);
        cuota.setFechaActualizacion(LocalDateTime.now());
        cuota.setFechaApertura(LocalDateTime.now());
        if (cuota.getEstadoCuota() == null) {
            cuota.setEstadoCuota("activa");
        }

        // Validar estado
        if (!esEstadoValido(cuota.getEstadoCuota())) {
            throw new RuntimeException("Estado no válido. Debe ser: activa, cerrada o suspendida");
        }

        return cuotaRepository.save(cuota);
    }

    @Override
    public Cuota actualizarCuota(Cuota cuota) {
        if (cuota.getIdCuota() == null) {
            throw new RuntimeException("No se puede actualizar una cuota sin ID");
        }

        Cuota cuotaExistente = obtenerCuotaPorId(cuota.getIdCuota());

        // Validar que la cuota no esté cerrada
        if ("cerrada".equals(cuotaExistente.getEstadoCuota())) {
            throw new RuntimeException("No se puede actualizar una cuota cerrada");
        }

        // Actualizar campos si no son null
        if (cuota.getEvento() != null && cuota.getEvento().getIdEvento() != null) {
            EventoDeportivo evento = eventoRepository.findById(cuota.getEvento().getIdEvento())
                    .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + cuota.getEvento().getIdEvento()));
            cuotaExistente.setEvento(evento);
        }

        if (cuota.getDescripcionCuota() != null) {
            cuotaExistente.setDescripcionCuota(cuota.getDescripcionCuota());
        }
        if (cuota.getValorCuota() != null) {
            if (cuota.getValorCuota().compareTo(BigDecimal.ONE) < 0) {
                throw new RuntimeException("El valor de la cuota debe ser mayor o igual a 1");
            }
            cuotaExistente.setValorCuota(cuota.getValorCuota());
        }
        if (cuota.getEstadoCuota() != null) {
            if (!esEstadoValido(cuota.getEstadoCuota())) {
                throw new RuntimeException("Estado no válido. Debe ser: activa, cerrada o suspendida");
            }
            cuotaExistente.setEstadoCuota(cuota.getEstadoCuota());
        }
        if (cuota.getFechaCierre() != null) {
            cuotaExistente.setFechaCierre(cuota.getFechaCierre());
        }

        cuotaExistente.setFechaActualizacion(LocalDateTime.now());
        return cuotaRepository.save(cuotaExistente);
    }

    @Override
    public void eliminarCuota(Long id) {
        Cuota cuota = obtenerCuotaPorId(id);
        if ("cerrada".equals(cuota.getEstadoCuota())) {
            throw new RuntimeException("No se puede eliminar una cuota cerrada");
        }
        cuotaRepository.deleteById(id);
    }

    @Override
    public List<Cuota> obtenerCuotasPorEvento(Long idEvento) {
        return cuotaRepository.findAll().stream()
                .filter(c -> c.getEvento().getIdEvento().equals(idEvento))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cuota> obtenerCuotasActivas() {
        return cuotaRepository.findAll().stream()
                .filter(c -> "activa".equals(c.getEstadoCuota()))
                .collect(Collectors.toList());
    }

    @Override
    public Cuota cerrarCuota(Long id) {
        Cuota cuota = obtenerCuotaPorId(id);
        if (!"activa".equals(cuota.getEstadoCuota())) {
            throw new RuntimeException("Solo se pueden cerrar cuotas activas");
        }
        cuota.setEstadoCuota("cerrada");
        cuota.setFechaCierre(LocalDateTime.now());
        cuota.setFechaActualizacion(LocalDateTime.now());
        return cuotaRepository.save(cuota);
    }

    @Override
    public Cuota suspenderCuota(Long id) {
        Cuota cuota = obtenerCuotaPorId(id);
        if ("cerrada".equals(cuota.getEstadoCuota())) {
            throw new RuntimeException("No se puede suspender una cuota cerrada");
        }
        cuota.setEstadoCuota("suspendida");
        cuota.setFechaActualizacion(LocalDateTime.now());
        return cuotaRepository.save(cuota);
    }

    private boolean esEstadoValido(String estado) {
        return estado != null && (
            estado.equals("activa") ||
            estado.equals("cerrada") ||
            estado.equals("suspendida")
        );
    }
}
