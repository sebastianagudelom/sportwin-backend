package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Apuesta;
import com.sportwin.sportwin_backend.service.interfaces.ApuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apuestas")
@CrossOrigin(origins = "*")
public class ApuestaController {

    @Autowired
    private ApuestaService apuestaService;

    // Listar todas las apuestas
    @GetMapping
    public ResponseEntity<List<Apuesta>> getAllApuestas() {
        try {
            List<Apuesta> apuestas = apuestaService.obtenerTodasLasApuestas();
            return ResponseEntity.ok(apuestas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar apuesta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Apuesta> getApuestaById(@PathVariable Long id) {
        try {
            Apuesta apuesta = apuestaService.obtenerApuestaPorId(id);
            return ResponseEntity.ok(apuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear apuesta
    @PostMapping
    public ResponseEntity<?> createApuesta(@RequestBody Apuesta apuesta) {
        try {
            Apuesta apuestaCreada = apuestaService.crearApuesta(apuesta);
            return ResponseEntity.ok(apuestaCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear la apuesta: " + e.getMessage());
        }
    }

    // Actualizar apuesta
    @PutMapping("/{id}")
    public ResponseEntity<?> updateApuesta(@PathVariable Long id, @RequestBody Apuesta apuesta) {
        try {
            apuesta.setIdApuesta(id); // Asegurar que el ID sea el correcto
            Apuesta apuestaActualizada = apuestaService.actualizarApuesta(apuesta);
            return ResponseEntity.ok(apuestaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar la apuesta: " + e.getMessage());
        }
    }

    // Eliminar apuesta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApuesta(@PathVariable Long id) {
        try {
            apuestaService.eliminarApuesta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar la apuesta: " + e.getMessage());
        }
    }
}