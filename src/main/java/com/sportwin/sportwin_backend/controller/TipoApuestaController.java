package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.TipoApuesta;
import com.sportwin.sportwin_backend.repository.TipoApuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-apuesta")
@CrossOrigin(origins = "*")
public class TipoApuestaController {

    @Autowired
    private TipoApuestaRepository tipoApuestaRepository;

    // Listar todos los tipos de apuesta
    @GetMapping // http://localhost:8080/api/tipos-apuesta
    public List<TipoApuesta> getAllTiposApuesta() {
        return tipoApuestaRepository.findAll();
    }

    // Buscar tipo de apuesta por ID
    @GetMapping("/{id}") // http://localhost:8080/api/tipos-apuesta/{id}
    public ResponseEntity<TipoApuesta> getTipoApuestaById(@PathVariable Long id) {
        return tipoApuestaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear tipo de apuesta
    @PostMapping // http://localhost:8080/api/tipos-apuesta
    public TipoApuesta createTipoApuesta(@RequestBody TipoApuesta tipoApuesta) {
        return tipoApuestaRepository.save(tipoApuesta);
    }

    // Actualizar tipo de apuesta
    @PutMapping("/{id}") // http://localhost:8080/api/tipos-apuesta/{id}
    public TipoApuesta updateTipoApuesta(@PathVariable Long id, @RequestBody TipoApuesta tipoApuesta) {
        TipoApuesta tipoExistente = tipoApuestaRepository.findById(id).orElse(null);
        if (tipoExistente != null) {
            tipoExistente.setIdTipoApuesta(id);
            tipoExistente.setNombre(tipoApuesta.getNombre());
            tipoExistente.setDescripcion(tipoApuesta.getDescripcion());
            tipoExistente.setEstado(tipoApuesta.getEstado());
            tipoExistente.setTipoResultado(tipoApuesta.getTipoResultado());
            return tipoApuestaRepository.save(tipoExistente);
        }
        return null;
    }

    // Eliminar tipo de apuesta
    @DeleteMapping("/{id}") // http://localhost:8080/api/tipos-apuesta/{id}
    public ResponseEntity<Void> deleteTipoApuesta(@PathVariable Long id) {
        tipoApuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
