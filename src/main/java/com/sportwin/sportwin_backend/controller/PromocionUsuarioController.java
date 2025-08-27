package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.PromocionUsuario;
import com.sportwin.sportwin_backend.repository.PromocionUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/promociones-usuario")
@CrossOrigin(origins = "*")
public class PromocionUsuarioController {

    @Autowired
    private PromocionUsuarioRepository promocionUsuarioRepository;

    @GetMapping
    public List<PromocionUsuario> getAllPromocionesUsuario() {
        return promocionUsuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromocionUsuario> getPromocionUsuarioById(@PathVariable Integer id) {
        return promocionUsuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<PromocionUsuario> getPromocionesUsuarioByUsuario(@PathVariable Integer idUsuario) {
        return promocionUsuarioRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @GetMapping("/usuario/{idUsuario}/activas")
    public List<PromocionUsuario> getPromocionesActivasByUsuario(@PathVariable Integer idUsuario) {
        return promocionUsuarioRepository.findActivePromotionsByUser(idUsuario);
    }

    @GetMapping("/promocion/{idPromocion}")
    public List<PromocionUsuario> getPromocionesUsuarioByPromocion(@PathVariable Integer idPromocion) {
        return promocionUsuarioRepository.findByPromocion_IdPromocion(idPromocion);
    }

    @GetMapping("/estado/{estado}")
    public List<PromocionUsuario> getPromocionesUsuarioByEstado(
            @PathVariable PromocionUsuario.EstadoPromocionUsuario estado) {
        return promocionUsuarioRepository.findByEstadoPromocion(estado);
    }

    @PostMapping
    public PromocionUsuario createPromocionUsuario(@RequestBody PromocionUsuario promocionUsuario) {
        return promocionUsuarioRepository.save(promocionUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromocionUsuario> updatePromocionUsuario(
            @PathVariable Integer id, 
            @RequestBody PromocionUsuario promocionUsuario) {
        return promocionUsuarioRepository.findById(id)
                .map(existingPromocion -> {
                    promocionUsuario.setIdPromocionUsuario(id);
                    return ResponseEntity.ok(promocionUsuarioRepository.save(promocionUsuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromocionUsuario(@PathVariable Integer id) {
        return promocionUsuarioRepository.findById(id)
                .map(promocion -> {
                    promocionUsuarioRepository.delete(promocion);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/actualizar-progreso")
    public ResponseEntity<PromocionUsuario> actualizarProgreso(
            @PathVariable Integer id,
            @RequestParam(required = false) Integer apuestasRealizadas,
            @RequestParam(required = false) Double montoApostado) {
        return promocionUsuarioRepository.findById(id)
                .map(promocionUsuario -> {
                    if (apuestasRealizadas != null) {
                        promocionUsuario.setNumeroApuestasRealizadas(apuestasRealizadas);
                        if (apuestasRealizadas >= promocionUsuario.getNumeroApuestasRequeridas()) {
                            promocionUsuario.setEstadoPromocion(PromocionUsuario.EstadoPromocionUsuario.completada);
                            promocionUsuario.setFechaCompletacion(LocalDateTime.now());
                        }
                    }
                    if (montoApostado != null) {
                        promocionUsuario.setMontoApostadoActual(promocionUsuario.getMontoApostadoActual()
                                .add(new java.math.BigDecimal(montoApostado)));
                        if (promocionUsuario.getMontoApostadoActual()
                                .compareTo(promocionUsuario.getMontoApostadoRequerido()) >= 0) {
                            promocionUsuario.setEstadoPromocion(PromocionUsuario.EstadoPromocionUsuario.completada);
                            promocionUsuario.setFechaCompletacion(LocalDateTime.now());
                        }
                    }
                    return ResponseEntity.ok(promocionUsuarioRepository.save(promocionUsuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PromocionUsuario> cancelarPromocion(@PathVariable Integer id) {
        return promocionUsuarioRepository.findById(id)
                .map(promocionUsuario -> {
                    promocionUsuario.setEstadoPromocion(PromocionUsuario.EstadoPromocionUsuario.cancelada);
                    return ResponseEntity.ok(promocionUsuarioRepository.save(promocionUsuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
