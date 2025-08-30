package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.PromocionUsuario;
import com.sportwin.sportwin_backend.service.interfaces.PromocionUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/promociones-usuario")
@CrossOrigin(origins = "*")
public class PromocionUsuarioController {

    @Autowired
    private PromocionUsuarioService promocionUsuarioService;

    @GetMapping
    public ResponseEntity<List<PromocionUsuario>> getAllPromocionesUsuario() {
        try {
            return ResponseEntity.ok(promocionUsuarioService.obtenerTodasLasPromocionesUsuario());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPromocionUsuarioById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(promocionUsuarioService.obtenerPromocionUsuarioPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PromocionUsuario>> getPromocionesUsuarioByUsuario(@PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(promocionUsuarioService.obtenerPromocionesUsuarioPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/usuario/{idUsuario}/activas")
    public ResponseEntity<List<PromocionUsuario>> getPromocionesActivasByUsuario(@PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(promocionUsuarioService.obtenerPromocionesActivasPorUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/promocion/{idPromocion}")
    public ResponseEntity<List<PromocionUsuario>> getPromocionesUsuarioByPromocion(@PathVariable Integer idPromocion) {
        try {
            return ResponseEntity.ok(promocionUsuarioService.obtenerPromocionesUsuarioPorPromocion(idPromocion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PromocionUsuario>> getPromocionesUsuarioByEstado(
            @PathVariable PromocionUsuario.EstadoPromocionUsuario estado) {
        try {
            return ResponseEntity.ok(promocionUsuarioService.obtenerPromocionesUsuarioPorEstado(estado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createPromocionUsuario(@RequestBody PromocionUsuario promocionUsuario) {
        try {
            return ResponseEntity.ok(promocionUsuarioService.crearPromocionUsuario(promocionUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePromocionUsuario(
            @PathVariable Integer id, 
            @RequestBody PromocionUsuario promocionUsuario) {
        try {
            return ResponseEntity.ok(promocionUsuarioService.actualizarPromocionUsuario(id, promocionUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePromocionUsuario(@PathVariable Integer id) {
        try {
            promocionUsuarioService.eliminarPromocionUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/actualizar-progreso")
    public ResponseEntity<?> actualizarProgreso(
            @PathVariable Integer id,
            @RequestParam(required = false) Integer apuestasRealizadas,
            @RequestParam(required = false) Double montoApostado) {
        try {
            BigDecimal monto = montoApostado != null ? new BigDecimal(montoApostado) : null;
            return ResponseEntity.ok(promocionUsuarioService.actualizarProgreso(id, apuestasRealizadas, monto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPromocion(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(promocionUsuarioService.cancelarPromocion(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
