package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.HistorialUsuario;
import com.sportwin.sportwin_backend.repository.HistorialUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-usuario")
@CrossOrigin(origins = "*")
public class HistorialUsuarioController {

    @Autowired
    private HistorialUsuarioRepository historialUsuarioRepository;

    @GetMapping
    public List<HistorialUsuario> getAllHistoriales() {
        return historialUsuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialUsuario> getHistorialById(@PathVariable Integer id) {
        return historialUsuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<HistorialUsuario> getHistorialByUsuario(@PathVariable Integer idUsuario) {
        return historialUsuarioRepository.findByUsuario_IdUsuario(idUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistorialUsuario createHistorial(@RequestBody HistorialUsuario historialUsuario) {
        return historialUsuarioRepository.save(historialUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialUsuario> updateHistorial(@PathVariable Integer id, @RequestBody HistorialUsuario historialUsuario) {
        return historialUsuarioRepository.findById(id)
                .map(existingHistorial -> {
                    historialUsuario.setIdHistorial(id);
                    return ResponseEntity.ok(historialUsuarioRepository.save(historialUsuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorial(@PathVariable Integer id) {
        return historialUsuarioRepository.findById(id)
                .map(historial -> {
                    historialUsuarioRepository.delete(historial);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
