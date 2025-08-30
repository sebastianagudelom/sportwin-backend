package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.Promocion;
import com.sportwin.sportwin_backend.repository.PromocionRepository;
import com.sportwin.sportwin_backend.service.interfaces.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PromocionServiceImpl implements PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Override
    public List<Promocion> obtenerTodasLasPromociones() {
        return promocionRepository.findAll();
    }

    @Override
    public Promocion obtenerPromocionPorId(Integer id) {
        return promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la promoción con ID: " + id));
    }

    @Override
    public List<Promocion> obtenerPromocionesActivas() {
        return promocionRepository.findActivePromotions(LocalDateTime.now());
    }

    @Override
    public Promocion obtenerPromocionPorCodigo(String codigo) {
        return promocionRepository.findByCodigoPromocional(codigo)
                .orElseThrow(() -> new RuntimeException("No se encontró la promoción con código: " + codigo));
    }

    @Override
    public List<Promocion> obtenerPromocionesPorTipo(Promocion.TipoPromocion tipo) {
        return promocionRepository.findActivePromotionsByType(tipo);
    }

    @Override
    public List<Promocion> obtenerPromocionesPorEstado(Promocion.EstadoPromocion estado) {
        return promocionRepository.findByEstado(estado);
    }

    @Override
    public List<Promocion> obtenerPromocionesPorTipoUsuario(Boolean soloNuevos) {
        return promocionRepository.findBySoloNuevosUsuarios(soloNuevos);
    }

    @Override
    public Promocion crearPromocion(Promocion promocion) {
        validarPromocion(promocion);
        return promocionRepository.save(promocion);
    }

    @Override
    public Promocion actualizarPromocion(Integer id, Promocion promocion) {
        if (!promocionRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la promoción con ID: " + id);
        }

        validarPromocion(promocion);
        promocion.setIdPromocion(id);
        return promocionRepository.save(promocion);
    }

    @Override
    public void eliminarPromocion(Integer id) {
        if (!promocionRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la promoción con ID: " + id);
        }
        promocionRepository.deleteById(id);
    }

    @Override
    public Promocion incrementarUsoPromocion(Integer id) {
        Promocion promocion = obtenerPromocionPorId(id);
        
        promocion.setUsosActuales(promocion.getUsosActuales() + 1);
        
        // Verificar si se alcanzó el límite de usos
        if (promocion.getMaxUsosTotales() > 0 && 
            promocion.getUsosActuales() >= promocion.getMaxUsosTotales()) {
            promocion.setEstado(Promocion.EstadoPromocion.agotada);
        }
        
        return promocionRepository.save(promocion);
    }

    private void validarPromocion(Promocion promocion) {
        if (promocion == null) {
            throw new RuntimeException("La promoción no puede ser nula");
        }
        if (promocion.getNombrePromocion() == null || promocion.getNombrePromocion().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la promoción es requerido");
        }
        if (promocion.getDescripcion() == null || promocion.getDescripcion().trim().isEmpty()) {
            throw new RuntimeException("La descripción de la promoción es requerida");
        }
        if (promocion.getCodigoPromocional() == null || promocion.getCodigoPromocional().trim().isEmpty()) {
            throw new RuntimeException("El código promocional es requerido");
        }
        if (promocion.getFechaInicio() == null) {
            throw new RuntimeException("La fecha de inicio es requerida");
        }
        if (promocion.getFechaFin() == null) {
            throw new RuntimeException("La fecha de fin es requerida");
        }
        if (promocion.getFechaInicio().isAfter(promocion.getFechaFin())) {
            throw new RuntimeException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        if (promocion.getValorPromocion() == null || promocion.getValorPromocion().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El valor de la promoción debe ser mayor que cero");
        }
        if (promocion.getTipoPromocion() == null) {
            throw new RuntimeException("El tipo de promoción es requerido");
        }
        if (promocion.getEstado() == null) {
            promocion.setEstado(Promocion.EstadoPromocion.activa);
        }
    }
}
