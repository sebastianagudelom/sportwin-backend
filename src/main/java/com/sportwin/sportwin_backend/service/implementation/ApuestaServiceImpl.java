package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.Apuesta;
import com.sportwin.sportwin_backend.entity.Usuario;
import com.sportwin.sportwin_backend.entity.Cuota;
import com.sportwin.sportwin_backend.repository.ApuestaRepository;
import com.sportwin.sportwin_backend.repository.UsuarioRepository;
import com.sportwin.sportwin_backend.repository.CuotaRepository;
import com.sportwin.sportwin_backend.service.interfaces.ApuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;

@Service
public class ApuestaServiceImpl implements ApuestaService {

    @Autowired
    private ApuestaRepository apuestaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CuotaRepository cuotaRepository;

    @Override
    public Apuesta crearApuesta(Apuesta apuesta) {
        // Validaciones básicas
        if (apuesta.getUsuario() == null || apuesta.getUsuario().getIdUsuario() == null) {
            throw new RuntimeException("El usuario es requerido");
        }
        if (apuesta.getCuota() == null || apuesta.getCuota().getIdCuota() == null) {
            throw new RuntimeException("La cuota es requerida");
        }
        if (apuesta.getMonto() == null || apuesta.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser mayor a 0");
        }

        // Cargar las entidades relacionadas
        Usuario usuario = usuarioRepository.findById(apuesta.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Cuota cuota = cuotaRepository.findById(apuesta.getCuota().getIdCuota())
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));
        
        // Establecer las entidades relacionadas
        apuesta.setUsuario(usuario);
        apuesta.setCuota(cuota);
        
        // Por defecto, el estado de una nueva apuesta es "pendiente"
        apuesta.setEstado("pendiente");
        
        // Calcular ganancia potencial
        if (apuesta.getMonto() != null && cuota.getValorCuota() != null) {
            BigDecimal gananciaPotencial = apuesta.getMonto().multiply(cuota.getValorCuota());
            apuesta.setGananciaPotencial(gananciaPotencial);
        }
        
        return apuestaRepository.save(apuesta);
    }

    @Override
    public Apuesta obtenerApuestaPorId(Long id) {
        return apuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apuesta no encontrada con ID: " + id));
    }

    @Override
    public List<Apuesta> obtenerTodasLasApuestas() {
        return apuestaRepository.findAll();
    }

    @Override
    public List<Apuesta> obtenerApuestasPorUsuario(Long idUsuario) {
        return apuestaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public Apuesta actualizarEstadoApuesta(Long id, String nuevoEstado) {
        Apuesta apuesta = obtenerApuestaPorId(id);
        
        // Validar que el nuevo estado sea válido
        if (!esEstadoValido(nuevoEstado)) {
            throw new RuntimeException("Estado de apuesta no válido: " + nuevoEstado);
        }
        
        apuesta.setEstado(nuevoEstado);
        return apuestaRepository.save(apuesta);
    }

    @Override
    public Apuesta actualizarApuesta(Apuesta apuesta) {
        if (apuesta.getIdApuesta() == null) {
            throw new RuntimeException("No se puede actualizar una apuesta sin ID");
        }
        
        if (!apuestaRepository.existsById(apuesta.getIdApuesta())) {
            throw new RuntimeException("Apuesta no encontrada con ID: " + apuesta.getIdApuesta());
        }
        
        return apuestaRepository.save(apuesta);
    }

    @Override
    public void eliminarApuesta(Long id) {
        if (!apuestaRepository.existsById(id)) {
            throw new RuntimeException("Apuesta no encontrada con ID: " + id);
        }
        apuestaRepository.deleteById(id);
    }
    
    private boolean esEstadoValido(String estado) {
        return estado != null && (
            estado.equals("pendiente") ||
            estado.equals("ganada") ||
            estado.equals("perdida")
        );
    }
}
