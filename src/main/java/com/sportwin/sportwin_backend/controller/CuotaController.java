package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Cuota;
import com.sportwin.sportwin_backend.service.interfaces.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
@CrossOrigin(origins = "*")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;

    // Listar todas las cuotas
    @GetMapping
    public ResponseEntity<List<Cuota>> getAllCuotas() {
        try {
            List<Cuota> cuotas = cuotaService.obtenerTodasLasCuotas();
            return ResponseEntity.ok(cuotas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar cuota por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cuota> getCuotaById(@PathVariable Long id) {
        try {
            Cuota cuota = cuotaService.obtenerCuotaPorId(id);
            return ResponseEntity.ok(cuota);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener cuotas por evento
    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<Cuota>> getCuotasByEvento(@PathVariable Long idEvento) {
        try {
            List<Cuota> cuotas = cuotaService.obtenerCuotasPorEvento(idEvento);
            return ResponseEntity.ok(cuotas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Obtener cuotas activas
    @GetMapping("/activas")
    public ResponseEntity<List<Cuota>> getCuotasActivas() {
        try {
            List<Cuota> cuotas = cuotaService.obtenerCuotasActivas();
            return ResponseEntity.ok(cuotas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Crear cuota
    @PostMapping
    public ResponseEntity<?> createCuota(@RequestBody Cuota cuota) {
        try {
            Cuota nuevaCuota = cuotaService.crearCuota(cuota);
            return ResponseEntity.ok(nuevaCuota);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear la cuota: " + e.getMessage());
        }
    }

    // Actualizar cuota
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCuota(@PathVariable Long id, @RequestBody Cuota cuota) {
        try {
            cuota.setIdCuota(id);
            Cuota cuotaActualizada = cuotaService.actualizarCuota(cuota);
            return ResponseEntity.ok(cuotaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al actualizar la cuota: " + e.getMessage());
        }
    }

    // Cerrar cuota
    @PutMapping("/{id}/cerrar")
    public ResponseEntity<?> cerrarCuota(@PathVariable Long id) {
        try {
            Cuota cuotaCerrada = cuotaService.cerrarCuota(id);
            return ResponseEntity.ok(cuotaCerrada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al cerrar la cuota: " + e.getMessage());
        }
    }

    // Suspender cuota
    @PutMapping("/{id}/suspender")
    public ResponseEntity<?> suspenderCuota(@PathVariable Long id) {
        try {
            Cuota cuotaSuspendida = cuotaService.suspenderCuota(id);
            return ResponseEntity.ok(cuotaSuspendida);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al suspender la cuota: " + e.getMessage());
        }
    }

    // Eliminar cuota
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCuota(@PathVariable Long id) {
        try {
            cuotaService.eliminarCuota(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al eliminar la cuota: " + e.getMessage());
        }
    }
}
