package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Deporte;
import com.sportwin.sportwin_backend.repository.DeporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deportes")
@CrossOrigin(origins = "*")
public class DeporteController {

    @Autowired
    private DeporteRepository deporteRepository;

    // Listar todos los deportes
    @GetMapping // http://localhost:8080/api/deportes
    public List<Deporte> getAllDeportes() {
        return deporteRepository.findAll();
    }

    // Buscar deporte por ID
    @GetMapping("/{id}") // http://localhost:8080/api/deportes/{id}
    public ResponseEntity<Deporte> getDeporteById(@PathVariable Long id) {
        return deporteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear deporte
    @PostMapping // http://localhost:8080/api/deportes
    public Deporte createDeporte(@RequestBody Deporte deporte) {
        return deporteRepository.save(deporte);
    }

    // Actualizar deporte
    @PutMapping("/{id}") // http://localhost:8080/api/deportes/{id}
    public Deporte updateDeporte(@PathVariable Long id, @RequestBody Deporte deporte) {
        Deporte deporteExistente = deporteRepository.findById(id).orElse(null);
        if (deporteExistente != null) {
            deporteExistente.setIdDeporte(id);
            deporteExistente.setNombreDeporte(deporte.getNombreDeporte());
            deporteExistente.setDescripcion(deporte.getDescripcion());
            return deporteRepository.save(deporte);
        }
        return null;
    }

    // Eliminar deporte
    @DeleteMapping("/{id}") // http://localhost:8080/api/deportes/{id}
    public ResponseEntity<Void> deleteDeporte(@PathVariable Long id) {
        deporteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
