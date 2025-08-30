package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.PromocionUsuario;
import com.sportwin.sportwin_backend.repository.PromocionUsuarioRepository;
import com.sportwin.sportwin_backend.repository.PromocionRepository;
import com.sportwin.sportwin_backend.repository.UsuarioRepository;
import com.sportwin.sportwin_backend.service.interfaces.PromocionUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PromocionUsuarioServiceImpl implements PromocionUsuarioService {

    @Autowired
    private PromocionUsuarioRepository promocionUsuarioRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<PromocionUsuario> obtenerTodasLasPromocionesUsuario() {
        return promocionUsuarioRepository.findAll();
    }

    @Override
    public PromocionUsuario obtenerPromocionUsuarioPorId(Integer id) {
        return promocionUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la promoción de usuario con ID: " + id));
    }

    @Override
    public List<PromocionUsuario> obtenerPromocionesUsuarioPorUsuario(Long idUsuario) {
        validarUsuarioExistente(idUsuario);
        return promocionUsuarioRepository.findByUsuario_IdUsuario(idUsuario.intValue());
    }

    @Override
    public List<PromocionUsuario> obtenerPromocionesActivasPorUsuario(Long idUsuario) {
        validarUsuarioExistente(idUsuario);
        return promocionUsuarioRepository.findActivePromotionsByUser(idUsuario.intValue());
    }

    @Override
    public List<PromocionUsuario> obtenerPromocionesUsuarioPorPromocion(Integer idPromocion) {
        validarPromocionExistente(idPromocion);
        return promocionUsuarioRepository.findByPromocion_IdPromocion(idPromocion);
    }

    @Override
    public List<PromocionUsuario> obtenerPromocionesUsuarioPorEstado(PromocionUsuario.EstadoPromocionUsuario estado) {
        if (estado == null) {
            throw new RuntimeException("El estado de la promoción es requerido");
        }
        return promocionUsuarioRepository.findByEstadoPromocion(estado);
    }

    @Override
    public PromocionUsuario crearPromocionUsuario(PromocionUsuario promocionUsuario) {
        validarPromocionUsuario(promocionUsuario);
        
        // Verificar si el usuario ya tiene esta promoción activa
        List<PromocionUsuario> promocionesActivas = promocionUsuarioRepository
                .findActivePromotionsByUser(promocionUsuario.getUsuario().getIdUsuario().intValue());
        
        if (promocionesActivas.stream().anyMatch(p -> 
                p.getPromocion().getIdPromocion().equals(promocionUsuario.getPromocion().getIdPromocion()))) {
            throw new RuntimeException("El usuario ya tiene esta promoción activa");
        }

        // Establecer valores por defecto
        promocionUsuario.setFechaCreacion(LocalDateTime.now());
        promocionUsuario.setEstadoPromocion(PromocionUsuario.EstadoPromocionUsuario.activa);
        promocionUsuario.setNumeroApuestasRealizadas(0);
        promocionUsuario.setMontoApostadoActual(BigDecimal.ZERO);

        return promocionUsuarioRepository.save(promocionUsuario);
    }

    @Override
    public PromocionUsuario actualizarPromocionUsuario(Integer id, PromocionUsuario promocionUsuario) {
        if (!promocionUsuarioRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la promoción de usuario con ID: " + id);
        }

        validarPromocionUsuario(promocionUsuario);
        PromocionUsuario existente = obtenerPromocionUsuarioPorId(id);
        
        // No permitir cambiar usuario o promoción
        if (!existente.getUsuario().getIdUsuario().equals(promocionUsuario.getUsuario().getIdUsuario()) ||
            !existente.getPromocion().getIdPromocion().equals(promocionUsuario.getPromocion().getIdPromocion())) {
            throw new RuntimeException("No se puede cambiar el usuario o la promoción asociada");
        }

        promocionUsuario.setIdPromocionUsuario(id);
        promocionUsuario.setFechaCreacion(existente.getFechaCreacion());
        
        return promocionUsuarioRepository.save(promocionUsuario);
    }

    @Override
    public void eliminarPromocionUsuario(Integer id) {
        if (!promocionUsuarioRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la promoción de usuario con ID: " + id);
        }
        promocionUsuarioRepository.deleteById(id);
    }

    @Override
    public PromocionUsuario actualizarProgreso(Integer id, Integer apuestasRealizadas, BigDecimal montoApostado) {
        PromocionUsuario promocionUsuario = obtenerPromocionUsuarioPorId(id);

        if (promocionUsuario.getEstadoPromocion() != PromocionUsuario.EstadoPromocionUsuario.activa) {
            throw new RuntimeException("Solo se puede actualizar el progreso de promociones activas");
        }

        if (apuestasRealizadas != null) {
            promocionUsuario.setNumeroApuestasRealizadas(apuestasRealizadas);
            if (apuestasRealizadas >= promocionUsuario.getNumeroApuestasRequeridas()) {
                promocionUsuario.setEstadoPromocion(PromocionUsuario.EstadoPromocionUsuario.completada);
                promocionUsuario.setFechaCompletacion(LocalDateTime.now());
            }
        }

        if (montoApostado != null) {
            promocionUsuario.setMontoApostadoActual(
                promocionUsuario.getMontoApostadoActual().add(montoApostado)
            );
            if (promocionUsuario.getMontoApostadoActual()
                    .compareTo(promocionUsuario.getMontoApostadoRequerido()) >= 0) {
                promocionUsuario.setEstadoPromocion(PromocionUsuario.EstadoPromocionUsuario.completada);
                promocionUsuario.setFechaCompletacion(LocalDateTime.now());
            }
        }

        return promocionUsuarioRepository.save(promocionUsuario);
    }

    @Override
    public PromocionUsuario cancelarPromocion(Integer id) {
        PromocionUsuario promocionUsuario = obtenerPromocionUsuarioPorId(id);
        
        if (promocionUsuario.getEstadoPromocion() != PromocionUsuario.EstadoPromocionUsuario.activa) {
            throw new RuntimeException("Solo se pueden cancelar promociones activas");
        }

        promocionUsuario.setEstadoPromocion(PromocionUsuario.EstadoPromocionUsuario.cancelada);
        return promocionUsuarioRepository.save(promocionUsuario);
    }

    private void validarPromocionUsuario(PromocionUsuario promocionUsuario) {
        if (promocionUsuario == null) {
            throw new RuntimeException("La promoción de usuario no puede ser nula");
        }
        if (promocionUsuario.getUsuario() == null || promocionUsuario.getUsuario().getIdUsuario() == null) {
            throw new RuntimeException("El usuario es requerido");
        }
        if (promocionUsuario.getPromocion() == null || promocionUsuario.getPromocion().getIdPromocion() == null) {
            throw new RuntimeException("La promoción es requerida");
        }
        
        validarUsuarioExistente(promocionUsuario.getUsuario().getIdUsuario());
        validarPromocionExistente(promocionUsuario.getPromocion().getIdPromocion());
        
        if (promocionUsuario.getNumeroApuestasRequeridas() != null && promocionUsuario.getNumeroApuestasRequeridas() < 0) {
            throw new RuntimeException("El número de apuestas requeridas no puede ser negativo");
        }
        if (promocionUsuario.getMontoApostadoRequerido() != null && 
            promocionUsuario.getMontoApostadoRequerido().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("El monto apostado requerido no puede ser negativo");
        }
    }

    private void validarUsuarioExistente(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("No existe el usuario con ID: " + idUsuario);
        }
    }

    private void validarPromocionExistente(Integer idPromocion) {
        if (!promocionRepository.existsById(idPromocion)) {
            throw new RuntimeException("No existe la promoción con ID: " + idPromocion);
        }
    }
}
