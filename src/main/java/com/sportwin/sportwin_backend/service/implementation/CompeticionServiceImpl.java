package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.Competicion;
import com.sportwin.sportwin_backend.entity.Deporte;
import com.sportwin.sportwin_backend.repository.CompeticionRepository;
import com.sportwin.sportwin_backend.repository.DeporteRepository;
import com.sportwin.sportwin_backend.service.interfaces.CompeticionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompeticionServiceImpl implements CompeticionService {

    @Autowired
    private CompeticionRepository competicionRepository;
    
    @Autowired
    private DeporteRepository deporteRepository;

    @Override
    public List<Competicion> obtenerTodasLasCompeticiones() {
        return competicionRepository.findAll();
    }

    @Override
    public Competicion obtenerCompeticionPorId(Long id) {
        return competicionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competición no encontrada con ID: " + id));
    }

    @Override
    public Competicion crearCompeticion(Competicion competicion) {
        // Validaciones básicas
        if (competicion.getNombreCompeticion() == null || competicion.getNombreCompeticion().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la competición es requerido");
        }
        if (competicion.getDeporte() == null || competicion.getDeporte().getIdDeporte() == null) {
            throw new RuntimeException("El deporte es requerido");
        }
        
        // Verificar que el deporte existe
        Deporte deporte = deporteRepository.findById(competicion.getDeporte().getIdDeporte())
                .orElseThrow(() -> new RuntimeException("Deporte no encontrado con ID: " + competicion.getDeporte().getIdDeporte()));
        
        // Establecer el deporte
        competicion.setDeporte(deporte);
        
        // Establecer estado por defecto si no se proporciona
        if (competicion.getEstado() == null || competicion.getEstado().trim().isEmpty()) {
            competicion.setEstado("activa");
        }
        
        // Validar estado válido
        if (!esEstadoValido(competicion.getEstado())) {
            throw new RuntimeException("Estado no válido. Debe ser: activa, finalizada o suspendida");
        }
        
        return competicionRepository.save(competicion);
    }

    @Override
    public Competicion actualizarCompeticion(Competicion competicion) {
        if (competicion.getIdCompeticion() == null) {
            throw new RuntimeException("No se puede actualizar una competición sin ID");
        }
        
        // Verificar que la competición existe
        Competicion competicionExistente = obtenerCompeticionPorId(competicion.getIdCompeticion());
        
        // Si se proporciona un deporte, verificar que existe
        if (competicion.getDeporte() != null && competicion.getDeporte().getIdDeporte() != null) {
            Deporte deporte = deporteRepository.findById(competicion.getDeporte().getIdDeporte())
                    .orElseThrow(() -> new RuntimeException("Deporte no encontrado con ID: " + competicion.getDeporte().getIdDeporte()));
            competicionExistente.setDeporte(deporte);
        }
        
        // Actualizar campos si no son null
        if (competicion.getNombreCompeticion() != null) {
            competicionExistente.setNombreCompeticion(competicion.getNombreCompeticion());
        }
        if (competicion.getLugar() != null) {
            competicionExistente.setLugar(competicion.getLugar());
        }
        if (competicion.getTemporadaActual() != null) {
            competicionExistente.setTemporadaActual(competicion.getTemporadaActual());
        }
        if (competicion.getEstado() != null) {
            if (!esEstadoValido(competicion.getEstado())) {
                throw new RuntimeException("Estado no válido. Debe ser: activa, finalizada o suspendida");
            }
            competicionExistente.setEstado(competicion.getEstado());
        }
        if (competicion.getFechaInicio() != null) {
            competicionExistente.setFechaInicio(competicion.getFechaInicio());
        }
        if (competicion.getFechaFin() != null) {
            competicionExistente.setFechaFin(competicion.getFechaFin());
        }
        if (competicion.getDescripcion() != null) {
            competicionExistente.setDescripcion(competicion.getDescripcion());
        }
        if (competicion.getLogoUrl() != null) {
            competicionExistente.setLogoUrl(competicion.getLogoUrl());
        }
        
        return competicionRepository.save(competicionExistente);
    }

    @Override
    public void eliminarCompeticion(Long id) {
        if (!competicionRepository.existsById(id)) {
            throw new RuntimeException("Competición no encontrada con ID: " + id);
        }
        competicionRepository.deleteById(id);
    }

    @Override
    public List<Competicion> obtenerCompeticionesPorDeporte(Long idDeporte) {
        return competicionRepository.findAll().stream()
                .filter(c -> c.getDeporte().getIdDeporte().equals(idDeporte))
                .collect(Collectors.toList());
    }

    @Override
    public List<Competicion> obtenerCompeticionesActivas() {
        return competicionRepository.findAll().stream()
                .filter(c -> "activa".equals(c.getEstado()))
                .collect(Collectors.toList());
    }
    
    private boolean esEstadoValido(String estado) {
        return estado != null && (
            estado.equals("activa") ||
            estado.equals("finalizada") ||
            estado.equals("suspendida")
        );
    }
}
