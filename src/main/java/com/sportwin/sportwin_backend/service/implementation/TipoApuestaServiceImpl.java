package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.TipoApuesta;
import com.sportwin.sportwin_backend.repository.TipoApuestaRepository;
import com.sportwin.sportwin_backend.service.interfaces.TipoApuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoApuestaServiceImpl implements TipoApuestaService {

    @Autowired
    private TipoApuestaRepository tipoApuestaRepository;

    @Override
    public List<TipoApuesta> obtenerTodosLosTiposApuesta() {
        return tipoApuestaRepository.findAll();
    }

    @Override
    public TipoApuesta obtenerTipoApuestaPorId(Long id) {
        return tipoApuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el tipo de apuesta con ID: " + id));
    }

    @Override
    public TipoApuesta crearTipoApuesta(TipoApuesta tipoApuesta) {
        validarTipoApuesta(tipoApuesta);
        return tipoApuestaRepository.save(tipoApuesta);
    }

    @Override
    public TipoApuesta actualizarTipoApuesta(Long id, TipoApuesta tipoApuesta) {
        if (!tipoApuestaRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el tipo de apuesta con ID: " + id);
        }

        validarTipoApuesta(tipoApuesta);
        tipoApuesta.setIdTipoApuesta(id);

        return tipoApuestaRepository.save(tipoApuesta);
    }

    @Override
    public void eliminarTipoApuesta(Long id) {
        if (!tipoApuestaRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el tipo de apuesta con ID: " + id);
        }
        
        // Aquí se podría agregar validación para verificar si el tipo de apuesta está siendo usado
        // en apuestas existentes antes de permitir su eliminación
        tipoApuestaRepository.deleteById(id);
    }

    private void validarTipoApuesta(TipoApuesta tipoApuesta) {
        if (tipoApuesta == null) {
            throw new RuntimeException("El tipo de apuesta no puede ser nulo");
        }
        if (tipoApuesta.getNombre() == null || tipoApuesta.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del tipo de apuesta es requerido");
        }
        if (tipoApuesta.getDescripcion() == null || tipoApuesta.getDescripcion().trim().isEmpty()) {
            throw new RuntimeException("La descripción del tipo de apuesta es requerida");
        }
        if (tipoApuesta.getTipoResultado() == null) {
            throw new RuntimeException("El tipo de resultado es requerido");
        }
        if (tipoApuesta.getEstado() == null) {
            throw new RuntimeException("El estado del tipo de apuesta es requerido");
        }
    }
}
