package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Transaccion;
import com.sportwin.sportwin_backend.service.interfaces.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "*")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping
    public ResponseEntity<List<Transaccion>> getTransacciones() {
        try {
            return ResponseEntity.ok(transaccionService.obtenerTodasLasTransacciones());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaccionById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(transaccionService.obtenerTransaccionPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> getTransaccionesByUsuario(@PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(transaccionService.obtenerTransaccionesPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> getTransaccionesByTipo(@PathVariable String tipo) {
        try {
            return ResponseEntity.ok(transaccionService.obtenerTransaccionesPorTipo(tipo));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getTransaccionesByEstado(@PathVariable String estado) {
        try {
            return ResponseEntity.ok(transaccionService.obtenerTransaccionesPorEstado(estado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTransaccion(@RequestBody Transaccion transaccion) {
        try {
            return ResponseEntity.ok(transaccionService.crearTransaccion(transaccion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccion) {
        try {
            return ResponseEntity.ok(transaccionService.actualizarTransaccion(id, transaccion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> updateEstadoTransaccion(@PathVariable Long id, @RequestBody String estado) {
        try {
            return ResponseEntity.ok(transaccionService.actualizarEstadoTransaccion(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaccion(@PathVariable Long id) {
        try {
            transaccionService.eliminarTransaccion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
