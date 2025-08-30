package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.Deporte;
import com.sportwin.sportwin_backend.repository.DeporteRepository;
import com.sportwin.sportwin_backend.service.interfaces.DeporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeporteServiceImpl implements DeporteService {

    @Autowired
    private DeporteRepository deporteRepository;

    @Override
    public List<Deporte> obtenerTodosLosDeportes() {
        return deporteRepository.findAll();
    }

    @Override
    public Deporte obtenerDeportePorId(Long id) {
        return deporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el deporte con ID: " + id));
    }

    @Override
    public Deporte crearDeporte(Deporte deporte) {
        if (deporte.getNombreDeporte() == null || deporte.getNombreDeporte().trim().isEmpty()) {
            throw new RuntimeException("El nombre del deporte no puede estar vacío");
        }
        if (existeDeportePorNombre(deporte.getNombreDeporte())) {
            throw new RuntimeException("Ya existe un deporte con el nombre: " + deporte.getNombreDeporte());
        }
        return deporteRepository.save(deporte);
    }

    @Override
    public Deporte actualizarDeporte(Deporte deporte) {
        if (deporte.getIdDeporte() == null) {
            throw new RuntimeException("El ID del deporte no puede ser nulo");
        }
        if (!existeDeportePorId(deporte.getIdDeporte())) {
            throw new RuntimeException("No se encontró el deporte con ID: " + deporte.getIdDeporte());
        }
        if (deporte.getNombreDeporte() == null || deporte.getNombreDeporte().trim().isEmpty()) {
            throw new RuntimeException("El nombre del deporte no puede estar vacío");
        }

        // Verificar si el nuevo nombre ya existe en otro deporte
        Deporte deporteExistente = deporteRepository.findById(deporte.getIdDeporte()).get();
        if (!deporteExistente.getNombreDeporte().equals(deporte.getNombreDeporte()) && 
            existeDeportePorNombre(deporte.getNombreDeporte())) {
            throw new RuntimeException("Ya existe un deporte con el nombre: " + deporte.getNombreDeporte());
        }

        return deporteRepository.save(deporte);
    }

    @Override
    public void eliminarDeporte(Long id) {
        if (!existeDeportePorId(id)) {
            throw new RuntimeException("No se encontró el deporte con ID: " + id);
        }
        deporteRepository.deleteById(id);
    }

    @Override
    public boolean existeDeportePorId(Long id) {
        return deporteRepository.existsById(id);
    }

    @Override
    public boolean existeDeportePorNombre(String nombreDeporte) {
        return deporteRepository.findAll().stream()
                .anyMatch(d -> d.getNombreDeporte().equalsIgnoreCase(nombreDeporte.trim()));
    }
}
