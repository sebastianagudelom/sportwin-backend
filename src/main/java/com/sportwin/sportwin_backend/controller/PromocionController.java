package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Promocion;
import com.sportwin.sportwin_backend.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/promociones")
@CrossOrigin(origins = "*")
public class PromocionController {

    @Autowired
    private PromocionRepository promocionRepository;

    @GetMapping
    public List<Promocion> getAllPromociones() {
        return promocionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promocion> getPromocionById(@PathVariable Integer id) {
        return promocionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activas")
    public List<Promocion> getActivePromotions() {
        return promocionRepository.findActivePromotions(LocalDateTime.now());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Promocion> getPromocionByCodigo(@PathVariable String codigo) {
        return promocionRepository.findByCodigoPromocional(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public List<Promocion> getPromocionesByTipo(@PathVariable Promocion.TipoPromocion tipo) {
        return promocionRepository.findActivePromotionsByType(tipo);
    }

    @GetMapping("/estado/{estado}")
    public List<Promocion> getPromocionesByEstado(@PathVariable Promocion.EstadoPromocion estado) {
        return promocionRepository.findByEstado(estado);
    }

    @GetMapping("/nuevos-usuarios/{soloNuevos}")
    public List<Promocion> getPromocionesByTipoUsuario(@PathVariable Boolean soloNuevos) {
        return promocionRepository.findBySoloNuevosUsuarios(soloNuevos);
    }

    @PostMapping
    public Promocion createPromocion(@RequestBody Promocion promocion) {
        return promocionRepository.save(promocion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promocion> updatePromocion(@PathVariable Integer id, @RequestBody Promocion promocion) {
        return promocionRepository.findById(id)
                .map(existingPromocion -> {
                    promocion.setIdPromocion(id);
                    return ResponseEntity.ok(promocionRepository.save(promocion));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromocion(@PathVariable Integer id) {
        return promocionRepository.findById(id)
                .map(promocion -> {
                    promocionRepository.delete(promocion);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/incrementar-uso")
    public ResponseEntity<Promocion> incrementarUso(@PathVariable Integer id) {
        return promocionRepository.findById(id)
                .map(promocion -> {
                    promocion.setUsosActuales(promocion.getUsosActuales() + 1);
                    
                    // Verificar si se alcanzó el límite de usos
                    if (promocion.getMaxUsosTotales() > 0 && 
                        promocion.getUsosActuales() >= promocion.getMaxUsosTotales()) {
                        promocion.setEstado(Promocion.EstadoPromocion.agotada);
                    }
                    
                    return ResponseEntity.ok(promocionRepository.save(promocion));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
