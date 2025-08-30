package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.HistorialUsuario;
import com.sportwin.sportwin_backend.service.interfaces.HistorialUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/historial-usuario")
@CrossOrigin(origins = "*")
public class HistorialUsuarioController {

    @Autowired
    private HistorialUsuarioService historialUsuarioService;

    @GetMapping
    public ResponseEntity<List<HistorialUsuario>> getAllHistoriales() {
        try {
            List<HistorialUsuario> historiales = historialUsuarioService.obtenerTodosLosHistoriales();
            return ResponseEntity.ok(historiales);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHistorialById(@PathVariable Long id) {
        try {
            HistorialUsuario historial = historialUsuarioService.obtenerHistorialPorId(id);
            return ResponseEntity.ok(historial);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener el historial: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> getHistorialByUsuario(@PathVariable Long idUsuario) {
        try {
            HistorialUsuario historial = historialUsuarioService.obtenerHistorialPorUsuario(idUsuario);
            return ResponseEntity.ok(historial);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener el historial del usuario: " + e.getMessage());
        }
    }

    @GetMapping("/rango")
    public ResponseEntity<?> getHistorialesPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            List<HistorialUsuario> historiales = historialUsuarioService.obtenerHistorialesPorRango(fechaInicio, fechaFin);
            return ResponseEntity.ok(historiales);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener los historiales: " + e.getMessage());
        }
    }

    @GetMapping("/tipo/{tipoOperacion}")
    public ResponseEntity<?> getHistorialesPorTipo(@PathVariable String tipoOperacion) {
        try {
            List<HistorialUsuario> historiales = historialUsuarioService.obtenerHistorialesPorTipo(tipoOperacion);
            return ResponseEntity.ok(historiales);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener los historiales: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createHistorial(@RequestBody HistorialUsuario historialUsuario) {
        try {
            HistorialUsuario nuevoHistorial = historialUsuarioService.crearHistorial(historialUsuario);
            return ResponseEntity.ok(nuevoHistorial);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear el historial: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHistorial(@PathVariable Long id, @RequestBody HistorialUsuario historialUsuario) {
        try {
            HistorialUsuario historialActualizado = historialUsuarioService.actualizarHistorial(id, historialUsuario);
            return ResponseEntity.ok(historialActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al actualizar el historial: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistorial(@PathVariable Long id) {
        try {
            historialUsuarioService.eliminarHistorial(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al eliminar el historial: " + e.getMessage());
        }
    }
}
