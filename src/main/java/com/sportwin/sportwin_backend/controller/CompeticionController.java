package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Competicion;
import com.sportwin.sportwin_backend.service.interfaces.CompeticionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competiciones")
@CrossOrigin(origins = "*")
public class CompeticionController {

    @Autowired
    private CompeticionService competicionService;

    // Listar todas las competiciones
    @GetMapping
    public ResponseEntity<List<Competicion>> getAllCompeticiones() {
        try {
            List<Competicion> competiciones = competicionService.obtenerTodasLasCompeticiones();
            return ResponseEntity.ok(competiciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar competicion por ID
    @GetMapping("/{id}")
    public ResponseEntity<Competicion> getCompeticionById(@PathVariable Long id) {
        try {
            Competicion competicion = competicionService.obtenerCompeticionPorId(id);
            return ResponseEntity.ok(competicion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener competiciones por deporte
    @GetMapping("/deporte/{idDeporte}")
    public ResponseEntity<List<Competicion>> getCompeticionesByDeporte(@PathVariable Long idDeporte) {
        try {
            List<Competicion> competiciones = competicionService.obtenerCompeticionesPorDeporte(idDeporte);
            return ResponseEntity.ok(competiciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Obtener competiciones activas
    @GetMapping("/activas")
    public ResponseEntity<List<Competicion>> getCompeticionesActivas() {
        try {
            List<Competicion> competiciones = competicionService.obtenerCompeticionesActivas();
            return ResponseEntity.ok(competiciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Crear competicion
    @PostMapping
    public ResponseEntity<?> createCompeticion(@RequestBody Competicion competicion) {
        try {
            Competicion nuevaCompeticion = competicionService.crearCompeticion(competicion);
            return ResponseEntity.ok(nuevaCompeticion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear la competición: " + e.getMessage());
        }
    }

    // Actualizar competicion
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompeticion(@PathVariable Long id, @RequestBody Competicion competicion) {
        try {
            competicion.setIdCompeticion(id);
            Competicion competicionActualizada = competicionService.actualizarCompeticion(competicion);
            return ResponseEntity.ok(competicionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al actualizar la competición: " + e.getMessage());
        }
    }

    // Eliminar competicion
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompeticion(@PathVariable Long id) {
        try {
            competicionService.eliminarCompeticion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al eliminar la competición: " + e.getMessage());
        }
    }

}