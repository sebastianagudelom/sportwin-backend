package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Promocion;
import com.sportwin.sportwin_backend.service.interfaces.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promociones")
@CrossOrigin(origins = "*")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<Promocion>> getAllPromociones() {
        try {
            return ResponseEntity.ok(promocionService.obtenerTodasLasPromociones());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPromocionById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(promocionService.obtenerPromocionPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Promocion>> getActivePromotions() {
        try {
            return ResponseEntity.ok(promocionService.obtenerPromocionesActivas());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> getPromocionByCodigo(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(promocionService.obtenerPromocionPorCodigo(codigo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Promocion>> getPromocionesByTipo(@PathVariable Promocion.TipoPromocion tipo) {
        try {
            return ResponseEntity.ok(promocionService.obtenerPromocionesPorTipo(tipo));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Promocion>> getPromocionesByEstado(@PathVariable Promocion.EstadoPromocion estado) {
        try {
            return ResponseEntity.ok(promocionService.obtenerPromocionesPorEstado(estado));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/nuevos-usuarios/{soloNuevos}")
    public ResponseEntity<List<Promocion>> getPromocionesByTipoUsuario(@PathVariable Boolean soloNuevos) {
        try {
            return ResponseEntity.ok(promocionService.obtenerPromocionesPorTipoUsuario(soloNuevos));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createPromocion(@RequestBody Promocion promocion) {
        try {
            return ResponseEntity.ok(promocionService.crearPromocion(promocion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePromocion(@PathVariable Integer id, @RequestBody Promocion promocion) {
        try {
            return ResponseEntity.ok(promocionService.actualizarPromocion(id, promocion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePromocion(@PathVariable Integer id) {
        try {
            promocionService.eliminarPromocion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/incrementar-uso")
    public ResponseEntity<?> incrementarUso(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(promocionService.incrementarUsoPromocion(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
