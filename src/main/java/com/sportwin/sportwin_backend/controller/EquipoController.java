package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Equipo;
import com.sportwin.sportwin_backend.service.interfaces.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    // Listar todos los equipos
    @GetMapping // http://localhost:8080/api/equipos
    public ResponseEntity<List<Equipo>> getAllEquipos() {
        try {
            List<Equipo> equipos = equipoService.obtenerTodosLosEquipos();
            return ResponseEntity.ok(equipos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar equipo por ID
    @GetMapping("/{id}") // http://localhost:8080/api/equipos/{id}
    public ResponseEntity<?> getEquipoById(@PathVariable Long id) {
        try {
            Equipo equipo = equipoService.obtenerEquipoPorId(id);
            return ResponseEntity.ok(equipo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener el equipo: " + e.getMessage());
        }
    }

    // Obtener equipos por deporte
    @GetMapping("/deporte/{deporteId}") // http://localhost:8080/api/equipos/deporte/{deporteId}
    public ResponseEntity<?> getEquiposPorDeporte(@PathVariable Long deporteId) {
        try {
            List<Equipo> equipos = equipoService.obtenerEquiposPorDeporte(deporteId);
            return ResponseEntity.ok(equipos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener los equipos: " + e.getMessage());
        }
    }

    // Crear equipo
    @PostMapping //http://localhost:8080/api/equipos
    public ResponseEntity<?> createEquipo(@RequestBody Equipo equipo) {
        try {
            Equipo nuevoEquipo = equipoService.crearEquipo(equipo);
            return ResponseEntity.ok(nuevoEquipo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear el equipo: " + e.getMessage());
        }
    }

    // Actualizar equipo
    @PutMapping("/{id}") // http://localhost:8080/api/equipos/{id}
    public ResponseEntity<?> updateEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        try {
            equipo.setIdEquipo(id);
            Equipo equipoActualizado = equipoService.actualizarEquipo(equipo);
            return ResponseEntity.ok(equipoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al actualizar el equipo: " + e.getMessage());
        }
    }

    // Eliminar equipo
    @DeleteMapping("/{id}") // http://localhost:8080/api/equipos/{id}
    public ResponseEntity<?> deleteEquipo(@PathVariable Long id) {
        try {
            equipoService.eliminarEquipo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al eliminar el equipo: " + e.getMessage());
        }
    }
}
