package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.HistorialUsuario;
import com.sportwin.sportwin_backend.repository.HistorialUsuarioRepository;
import com.sportwin.sportwin_backend.repository.UsuarioRepository;
import com.sportwin.sportwin_backend.service.interfaces.HistorialUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialUsuarioServiceImpl implements HistorialUsuarioService {

    @Autowired
    private HistorialUsuarioRepository historialUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<HistorialUsuario> obtenerTodosLosHistoriales() {
        return historialUsuarioRepository.findAll();
    }

    @Override
    public HistorialUsuario obtenerHistorialPorId(Long id) {
        return historialUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el historial con ID: " + id));
    }

    @Override
    public HistorialUsuario obtenerHistorialPorUsuario(Long idUsuario) {
        validarUsuarioExistente(idUsuario);
        return historialUsuarioRepository.findByUsuario_IdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("No se encontró el historial para el usuario con ID: " + idUsuario));
    }

    @Override
    public HistorialUsuario crearHistorial(HistorialUsuario historialUsuario) {
        validarHistorialUsuario(historialUsuario);
        
        if (existeHistorialPorUsuario(historialUsuario.getUsuario().getIdUsuario())) {
            throw new RuntimeException("Ya existe un historial para el usuario con ID: " + 
                    historialUsuario.getUsuario().getIdUsuario());
        }

        // Inicializar valores por defecto
        if (historialUsuario.getTotalApostado() == null) historialUsuario.setTotalApostado(BigDecimal.ZERO);
        if (historialUsuario.getTotalGanado() == null) historialUsuario.setTotalGanado(BigDecimal.ZERO);
        if (historialUsuario.getTotalPerdido() == null) historialUsuario.setTotalPerdido(BigDecimal.ZERO);
        if (historialUsuario.getNumeroApuestasGanadas() == null) historialUsuario.setNumeroApuestasGanadas(0);
        if (historialUsuario.getNumeroApuestasPerdidas() == null) historialUsuario.setNumeroApuestasPerdidas(0);
        if (historialUsuario.getNumeroApuestasTotales() == null) historialUsuario.setNumeroApuestasTotales(0);
        if (historialUsuario.getPorcentajeAcierto() == null) historialUsuario.setPorcentajeAcierto(BigDecimal.ZERO);
        if (historialUsuario.getRachaActual() == null) historialUsuario.setRachaActual(0);
        if (historialUsuario.getMejorRachaGanadora() == null) historialUsuario.setMejorRachaGanadora(0);
        if (historialUsuario.getApuestaMayorGanancia() == null) historialUsuario.setApuestaMayorGanancia(BigDecimal.ZERO);
        if (historialUsuario.getFechaCreacion() == null) historialUsuario.setFechaCreacion(LocalDateTime.now());
        historialUsuario.setFechaActualizacion(LocalDateTime.now());

        return historialUsuarioRepository.save(historialUsuario);
    }

    @Override
    public HistorialUsuario actualizarHistorial(Long id, HistorialUsuario historialUsuario) {
        if (!existeHistorialPorId(id)) {
            throw new RuntimeException("No se encontró el historial con ID: " + id);
        }

        validarHistorialUsuario(historialUsuario);
        historialUsuario.setIdHistorial(id);

        // Verificar si se está intentando cambiar el usuario
        HistorialUsuario historialExistente = obtenerHistorialPorId(id);
        if (!historialExistente.getUsuario().getIdUsuario().equals(
                historialUsuario.getUsuario().getIdUsuario())) {
            throw new RuntimeException("No se puede cambiar el usuario asociado al historial");
        }

        // Mantener fecha de creación original y actualizar fecha de actualización
        historialUsuario.setFechaCreacion(historialExistente.getFechaCreacion());
        historialUsuario.setFechaActualizacion(LocalDateTime.now());

        // Calcular porcentaje de acierto
        if (historialUsuario.getNumeroApuestasTotales() > 0) {
            BigDecimal porcentaje = new BigDecimal(historialUsuario.getNumeroApuestasGanadas() * 100.0 / historialUsuario.getNumeroApuestasTotales());
            historialUsuario.setPorcentajeAcierto(porcentaje);
        }

        return historialUsuarioRepository.save(historialUsuario);
    }

    @Override
    public void eliminarHistorial(Long id) {
        if (!existeHistorialPorId(id)) {
            throw new RuntimeException("No se encontró el historial con ID: " + id);
        }
        historialUsuarioRepository.deleteById(id);
    }

    @Override
    public List<HistorialUsuario> obtenerHistorialesPorRango(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new RuntimeException("Las fechas de inicio y fin son requeridas");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new RuntimeException("La fecha de inicio no puede ser posterior a la fecha fin");
        }

        return historialUsuarioRepository.findAll().stream()
                .filter(h -> !h.getFechaUltimaApuesta().isBefore(fechaInicio) && 
                           !h.getFechaUltimaApuesta().isAfter(fechaFin))
                .collect(Collectors.toList());
    }

    @Override
    public List<HistorialUsuario> obtenerHistorialesPorTipo(String tipoOperacion) {
        if (tipoOperacion == null || tipoOperacion.trim().isEmpty()) {
            throw new RuntimeException("El tipo de operación es requerido");
        }

        // Modificar según el tipo de operación que quieras filtrar
        // Por ejemplo, podrías filtrar por rachaActual > 0 para rachas ganadoras
        return historialUsuarioRepository.findAll().stream()
                .filter(h -> {
                    switch (tipoOperacion.toLowerCase()) {
                        case "ganador":
                            return h.getTotalGanado().compareTo(h.getTotalPerdido()) > 0;
                        case "perdedor":
                            return h.getTotalPerdido().compareTo(h.getTotalGanado()) > 0;
                        case "racha_positiva":
                            return h.getRachaActual() > 0;
                        case "racha_negativa":
                            return h.getRachaActual() < 0;
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeHistorialPorId(Long id) {
        return historialUsuarioRepository.existsById(id);
    }

    @Override
    public boolean existeHistorialPorUsuario(Long idUsuario) {
        return historialUsuarioRepository.findByUsuario_IdUsuario(idUsuario).isPresent();
    }

    private void validarHistorialUsuario(HistorialUsuario historial) {
        if (historial == null) {
            throw new RuntimeException("El historial no puede ser nulo");
        }
        if (historial.getUsuario() == null || historial.getUsuario().getIdUsuario() == null) {
            throw new RuntimeException("El usuario es requerido");
        }

        // Validar montos no negativos
        if (historial.getTotalApostado() != null && historial.getTotalApostado().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("El total apostado no puede ser negativo");
        }
        if (historial.getTotalGanado() != null && historial.getTotalGanado().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("El total ganado no puede ser negativo");
        }
        if (historial.getTotalPerdido() != null && historial.getTotalPerdido().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("El total perdido no puede ser negativo");
        }

        // Validar números no negativos
        if (historial.getNumeroApuestasGanadas() != null && historial.getNumeroApuestasGanadas() < 0) {
            throw new RuntimeException("El número de apuestas ganadas no puede ser negativo");
        }
        if (historial.getNumeroApuestasPerdidas() != null && historial.getNumeroApuestasPerdidas() < 0) {
            throw new RuntimeException("El número de apuestas perdidas no puede ser negativo");
        }
        if (historial.getNumeroApuestasTotales() != null && historial.getNumeroApuestasTotales() < 0) {
            throw new RuntimeException("El número de apuestas totales no puede ser negativo");
        }

        // Validar porcentaje de acierto
        if (historial.getPorcentajeAcierto() != null && 
            (historial.getPorcentajeAcierto().compareTo(BigDecimal.ZERO) < 0 || 
             historial.getPorcentajeAcierto().compareTo(new BigDecimal("100")) > 0)) {
            throw new RuntimeException("El porcentaje de acierto debe estar entre 0 y 100");
        }

        // Validar que el usuario existe
        validarUsuarioExistente(historial.getUsuario().getIdUsuario());
    }

    private void validarUsuarioExistente(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("No existe el usuario con ID: " + idUsuario);
        }
    }
}
