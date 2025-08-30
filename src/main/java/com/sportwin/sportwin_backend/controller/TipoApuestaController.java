package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.TipoApuesta;
import com.sportwin.sportwin_backend.service.interfaces.TipoApuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-apuesta")
@CrossOrigin(origins = "*")
public class TipoApuestaController {

    @Autowired
    private TipoApuestaService tipoApuestaService;

    @GetMapping
    public ResponseEntity<List<TipoApuesta>> getAllTiposApuesta() {
        try {
            return ResponseEntity.ok(tipoApuestaService.obtenerTodosLosTiposApuesta());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTipoApuestaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tipoApuestaService.obtenerTipoApuestaPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTipoApuesta(@RequestBody TipoApuesta tipoApuesta) {
        try {
            return ResponseEntity.ok(tipoApuestaService.crearTipoApuesta(tipoApuesta));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTipoApuesta(@PathVariable Long id, @RequestBody TipoApuesta tipoApuesta) {
        try {
            return ResponseEntity.ok(tipoApuestaService.actualizarTipoApuesta(id, tipoApuesta));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTipoApuesta(@PathVariable Long id) {
        try {
            tipoApuestaService.eliminarTipoApuesta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
