package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Competicion;
import com.sportwin.sportwin_backend.entity.Deporte;
import com.sportwin.sportwin_backend.repository.CompeticionRepository;
import com.sportwin.sportwin_backend.repository.DeporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competiciones")
@CrossOrigin(origins = "*")
public class CompeticionController {

    @Autowired
    private CompeticionRepository competicionRepository;
    
    @Autowired
    private DeporteRepository deporteRepository;

    // Listar todas las competiciones
    @GetMapping // http://localhost:8080/api/competiciones
    public List<Competicion> getAllCompeticiones() {
        return competicionRepository.findAll();
    }

    // Buscar competicion por ID
    @GetMapping("/{id}") // http://localhost:8080/api/competiciones/{id}
    public ResponseEntity<Competicion> getCompeticionById(@PathVariable Long id) {
        return competicionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear competicion
    @PostMapping
    public ResponseEntity<Competicion> createCompeticion(@RequestBody Competicion competicion) {
        try {
            // Verificar que el deporte existe
            if (competicion.getDeporte() != null && competicion.getDeporte().getIdDeporte() != null) {
                Optional<Deporte> deporte = deporteRepository.findById(competicion.getDeporte().getIdDeporte());
                if (deporte.isPresent()) {
                    competicion.setDeporte(deporte.get());
                } else {
                    return ResponseEntity.badRequest().build(); // Deporte no existe
                }
            }
            
            Competicion competicionGuardada = competicionRepository.save(competicion);
            return ResponseEntity.ok(competicionGuardada);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Actualizar competicion
    @PutMapping("/{id}")
    public ResponseEntity<Competicion> updateCompeticion(@PathVariable Long id, @RequestBody Competicion competicionActualizada) {
        Optional<Competicion> competicionOptional = competicionRepository.findById(id);
        
        if (competicionOptional.isPresent()) {
            Competicion competicionExistente = competicionOptional.get();
            
            // Actualizar campos
            competicionExistente.setNombreCompeticion(competicionActualizada.getNombreCompeticion());
            competicionExistente.setLugar(competicionActualizada.getLugar());
            competicionExistente.setTemporadaActual(competicionActualizada.getTemporadaActual());
            competicionExistente.setEstado(competicionActualizada.getEstado());
            competicionExistente.setFechaInicio(competicionActualizada.getFechaInicio());
            competicionExistente.setFechaFin(competicionActualizada.getFechaFin());
            competicionExistente.setDescripcion(competicionActualizada.getDescripcion());
            competicionExistente.setLogoUrl(competicionActualizada.getLogoUrl());
            
            // Actualizar deporte si viene en la petición
            if (competicionActualizada.getDeporte() != null && competicionActualizada.getDeporte().getIdDeporte() != null) {
                Optional<Deporte> deporte = deporteRepository.findById(competicionActualizada.getDeporte().getIdDeporte());
                if (deporte.isPresent()) {
                    competicionExistente.setDeporte(deporte.get());
                }
            }
            
            Competicion competicionGuardada = competicionRepository.save(competicionExistente);
            return ResponseEntity.ok(competicionGuardada);
        }
        
        return ResponseEntity.notFound().build();
    }

    // Eliminar competicion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompeticion(@PathVariable Long id) {
        if (competicionRepository.existsById(id)) {
            competicionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}