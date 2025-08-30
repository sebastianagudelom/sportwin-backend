package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Deporte;
import com.sportwin.sportwin_backend.service.interfaces.DeporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deportes")
@CrossOrigin(origins = "*")
public class DeporteController {

    @Autowired
    private DeporteService deporteService;

    // Listar todos los deportes
    @GetMapping
    public ResponseEntity<List<Deporte>> getAllDeportes() {
        try {
            List<Deporte> deportes = deporteService.obtenerTodosLosDeportes();
            return ResponseEntity.ok(deportes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar deporte por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDeporteById(@PathVariable Long id) {
        try {
            Deporte deporte = deporteService.obtenerDeportePorId(id);
            return ResponseEntity.ok(deporte);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener el deporte: " + e.getMessage());
        }
    }

    // Crear deporte
    @PostMapping
    public ResponseEntity<?> createDeporte(@RequestBody Deporte deporte) {
        try {
            Deporte nuevoDeporte = deporteService.crearDeporte(deporte);
            return ResponseEntity.ok(nuevoDeporte);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear el deporte: " + e.getMessage());
        }
    }

    // Actualizar deporte
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeporte(@PathVariable Long id, @RequestBody Deporte deporte) {
        try {
            deporte.setIdDeporte(id);
            Deporte deporteActualizado = deporteService.actualizarDeporte(deporte);
            return ResponseEntity.ok(deporteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al actualizar el deporte: " + e.getMessage());
        }
    }

    // Eliminar deporte
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeporte(@PathVariable Long id) {
        try {
            deporteService.eliminarDeporte(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al eliminar el deporte: " + e.getMessage());
        }
    }
}
