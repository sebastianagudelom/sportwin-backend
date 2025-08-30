package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.Equipo;
import com.sportwin.sportwin_backend.repository.DeporteRepository;
import com.sportwin.sportwin_backend.repository.EquipoRepository;
import com.sportwin.sportwin_backend.service.interfaces.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private DeporteRepository deporteRepository;

    @Override
    public List<Equipo> obtenerTodosLosEquipos() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo obtenerEquipoPorId(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el equipo con ID: " + id));
    }

    @Override
    public Equipo crearEquipo(Equipo equipo) {
        validarEquipo(equipo);
        validarDeporte(equipo.getDeporte().getIdDeporte());
        
        if (existeEquipoPorNombre(equipo.getNombre())) {
            throw new RuntimeException("Ya existe un equipo con el nombre: " + equipo.getNombre());
        }

        return equipoRepository.save(equipo);
    }

    @Override
    public Equipo actualizarEquipo(Equipo equipo) {
        if (equipo.getIdEquipo() == null) {
            throw new RuntimeException("El ID del equipo no puede ser nulo");
        }

        Equipo equipoExistente = obtenerEquipoPorId(equipo.getIdEquipo());
        validarEquipo(equipo);
        validarDeporte(equipo.getDeporte().getIdDeporte());

        // Verificar si el nuevo nombre ya existe en otro equipo
        if (!equipoExistente.getNombre().equals(equipo.getNombre()) && 
            existeEquipoPorNombre(equipo.getNombre())) {
            throw new RuntimeException("Ya existe un equipo con el nombre: " + equipo.getNombre());
        }

        return equipoRepository.save(equipo);
    }

    @Override
    public void eliminarEquipo(Long id) {
        if (!existeEquipoPorId(id)) {
            throw new RuntimeException("No se encontró el equipo con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }

    @Override
    public boolean existeEquipoPorId(Long id) {
        return equipoRepository.existsById(id);
    }

    @Override
    public boolean existeEquipoPorNombre(String nombre) {
        return equipoRepository.findAll().stream()
                .anyMatch(e -> e.getNombre().equalsIgnoreCase(nombre.trim()));
    }

    @Override
    public List<Equipo> obtenerEquiposPorDeporte(Long deporteId) {
        validarDeporte(deporteId);
        return equipoRepository.findAll().stream()
                .filter(e -> e.getDeporte().getIdDeporte().equals(deporteId))
                .collect(Collectors.toList());
    }

    private void validarEquipo(Equipo equipo) {
        if (equipo == null) {
            throw new RuntimeException("El equipo no puede ser nulo");
        }
        if (equipo.getNombre() == null || equipo.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del equipo no puede estar vacío");
        }
        if (equipo.getDeporte() == null || equipo.getDeporte().getIdDeporte() == null) {
            throw new RuntimeException("El deporte del equipo es requerido");
        }
        if (equipo.getTipo() == null || equipo.getTipo().trim().isEmpty()) {
            throw new RuntimeException("El tipo de equipo no puede estar vacío");
        }
    }

    private void validarDeporte(Long deporteId) {
        if (!deporteRepository.existsById(deporteId)) {
            throw new RuntimeException("No se encontró el deporte con ID: " + deporteId);
        }
    }
}
