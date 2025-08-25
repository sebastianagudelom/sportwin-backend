package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Equipo;
import com.sportwin.sportwin_backend.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*")
public class EquipoController {

    @Autowired
    private EquipoRepository equipoRepository;

    // Listar todos los equipos
    @GetMapping // http://localhost:8080/api/equipos
    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    // Buscar equipo por ID
    @GetMapping("/{id}") // http://localhost:8080/api/equipos/{id}
    public ResponseEntity<Equipo> getEquipoById(@PathVariable Long id) {
        return equipoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear equipo
    @PostMapping // http://localhost:8080/api/equipos
    public Equipo createEquipo(@RequestBody Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    // Actualizar equipo
    @PutMapping("/{id}") // http://localhost:8080/api/equipos/{id}
    public Equipo updateEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        Equipo equipoExistente = equipoRepository.findById(id).orElse(null);
        if (equipoExistente != null) {
            equipoExistente.setIdEquipo(id);
            equipoExistente.setDeporte(equipo.getDeporte());
            equipoExistente.setNombre(equipo.getNombre());
            equipoExistente.setTipo(equipo.getTipo());
            return equipoRepository.save(equipoExistente);
        }
        return null;
    }

    // Eliminar equipo
    @DeleteMapping("/{id}") // http://localhost:8080/api/equipos/{id}
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        equipoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
