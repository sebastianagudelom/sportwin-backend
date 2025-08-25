package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Cuota;
import com.sportwin.sportwin_backend.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
@CrossOrigin(origins = "*")
public class CuotaController {

    @Autowired
    private CuotaRepository cuotaRepository;

    // Listar todas las cuotas
    @GetMapping // http://localhost:8080/api/cuotas
    public List<Cuota> getAllCuotas() {
        return cuotaRepository.findAll();
    }

    // Buscar cuota por ID
    @GetMapping("/{id}") // http://localhost:8080/api/cuotas/{id}
    public ResponseEntity<Cuota> getCuotaById(@PathVariable Long id) {
        return cuotaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear cuota
    @PostMapping // http://localhost:8080/api/cuotas
    public Cuota createCuota(@RequestBody Cuota cuota) {
        cuota.setFechaActualizacion(LocalDateTime.now());
        return cuotaRepository.save(cuota);
    }

    // Actualizar cuota
    @PutMapping("/{id}") // http://localhost:8080/api/cuotas/{id}
    public Cuota updateCuota(@PathVariable Long id, @RequestBody Cuota cuota) {
        Cuota cuotaExistente = cuotaRepository.findById(id).orElse(null);
        if (cuotaExistente != null) {
            cuotaExistente.setIdCuota(id);
            cuotaExistente.setEvento(cuota.getEvento());
            cuotaExistente.setDescripcionCuota(cuota.getDescripcionCuota());
            cuotaExistente.setValorCuota(cuota.getValorCuota());
            cuotaExistente.setEstadoCuota(cuota.getEstadoCuota());
            cuotaExistente.setFechaApertura(cuota.getFechaApertura());
            cuotaExistente.setFechaCierre(cuota.getFechaCierre());
            cuotaExistente.setFechaActualizacion(LocalDateTime.now());
            return cuotaRepository.save(cuotaExistente);
        }
        return null;
    }

    // Eliminar cuota
    @DeleteMapping("/{id}") // http://localhost:8080/api/cuotas/{id}
    public ResponseEntity<Void> deleteCuota(@PathVariable Long id) {
        cuotaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
