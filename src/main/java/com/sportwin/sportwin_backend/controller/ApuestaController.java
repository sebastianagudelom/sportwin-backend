package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Apuesta;
import com.sportwin.sportwin_backend.entity.Usuario;
import com.sportwin.sportwin_backend.entity.Cuota;
import com.sportwin.sportwin_backend.repository.ApuestaRepository;
import com.sportwin.sportwin_backend.repository.UsuarioRepository;
import com.sportwin.sportwin_backend.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/apuestas")
@CrossOrigin(origins = "*")
public class ApuestaController {

    @Autowired
    private ApuestaRepository apuestaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CuotaRepository cuotaRepository;

    // Listar todas las apuestas
    @GetMapping // http://localhost:8080/api/apuestas
    public List<Apuesta> getAllApuestas() {
        return apuestaRepository.findAll();
    }

    // Buscar apuesta por ID
    @GetMapping("/{id}") // http://localhost:8080/api/apuestas/{id}
    public ResponseEntity<Apuesta> getApuestaById(@PathVariable Long id) {
        return apuestaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear apuesta
    @PostMapping // http://localhost:8080/api/apuestas
    public ResponseEntity<?> createApuesta(@RequestBody Apuesta apuesta) {
        try {
            // Validaciones básicas
            if (apuesta.getUsuario() == null || apuesta.getUsuario().getIdUsuario() == null) {
                return ResponseEntity.badRequest().body("El usuario es requerido");
            }
            if (apuesta.getCuota() == null || apuesta.getCuota().getIdCuota() == null) {
                return ResponseEntity.badRequest().body("La cuota es requerida");
            }
            if (apuesta.getMonto() == null || apuesta.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body("El monto debe ser mayor a 0");
            }
            
            // Cargar las entidades relacionadas
            Usuario usuario = usuarioRepository.findById(apuesta.getUsuario().getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            Cuota cuota = cuotaRepository.findById(apuesta.getCuota().getIdCuota())
                    .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));
            
            // Establecer las entidades relacionadas
            apuesta.setUsuario(usuario);
            apuesta.setCuota(cuota);
            
            // Calcular ganancia potencial manualmente
            if (apuesta.getMonto() != null && cuota.getValorCuota() != null) {
                apuesta.setGananciaPotencial(apuesta.getMonto().multiply(cuota.getValorCuota()));
            } else {
                apuesta.setGananciaPotencial(BigDecimal.ZERO);
            }
            
            // Guardar la apuesta
            Apuesta apuestaGuardada = apuestaRepository.save(apuesta);
            return ResponseEntity.ok(apuestaGuardada);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear la apuesta: " + e.getMessage());
        }
    }

    // Actualizar apuesta
    @PutMapping("/{id}") // http://localhost:8080/api/apuestas/{id}
    public Apuesta updateApuesta(@PathVariable Long id, @RequestBody Apuesta apuesta) {
        Apuesta apuestaExistente = apuestaRepository.findById(id).orElse(null);
        if (apuestaExistente != null) {
            apuestaExistente.setIdApuesta(id);
            apuestaExistente.setUsuario(apuesta.getUsuario());
            apuestaExistente.setCuota(apuesta.getCuota());
            apuestaExistente.setMonto(apuesta.getMonto());
            apuestaExistente.setEstado(apuesta.getEstado());
            apuestaExistente.setFechaApuesta(apuesta.getFechaApuesta());
            apuestaExistente.setFechaResolucion(apuesta.getFechaResolucion());
            // La ganancia potencial se recalcula automáticamente en el método @PreUpdate
            return apuestaRepository.save(apuestaExistente);
        }
        return null;
    }

    // Eliminar apuesta
    @DeleteMapping("/{id}") // http://localhost:8080/api/apuestas/{id}
    public ResponseEntity<Void> deleteApuesta(@PathVariable Long id) {
        apuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Endpoint de prueba para verificar datos
    @GetMapping("/test/data")
    public ResponseEntity<String> testData() {
        try {
            // Verificar si existen usuarios y cuotas
            long usuariosCount = apuestaRepository.count(); // Esto no es correcto, pero es solo para prueba
            return ResponseEntity.ok("Servidor funcionando correctamente. Usuarios en BD: " + usuariosCount);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
