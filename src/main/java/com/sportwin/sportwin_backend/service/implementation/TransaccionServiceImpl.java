package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.Transaccion;
import com.sportwin.sportwin_backend.repository.TransaccionRepository;
import com.sportwin.sportwin_backend.repository.UsuarioRepository;
import com.sportwin.sportwin_backend.service.interfaces.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Transaccion> obtenerTodasLasTransacciones() {
        return transaccionRepository.findAll();
    }

    @Override
    public Transaccion obtenerTransaccionPorId(Long id) {
        return transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la transacción con ID: " + id));
    }

    @Override
    public List<Transaccion> obtenerTransaccionesPorUsuario(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("No existe el usuario con ID: " + idUsuario);
        }
        return transaccionRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public List<Transaccion> obtenerTransaccionesPorTipo(String tipo) {
        validarTipoTransaccion(tipo);
        return transaccionRepository.findByTipo(tipo);
    }

    @Override
    public List<Transaccion> obtenerTransaccionesPorEstado(String estado) {
        validarEstadoTransaccion(estado);
        return transaccionRepository.findByEstadoTransaccion(estado);
    }

    @Override
    public Transaccion crearTransaccion(Transaccion transaccion) {
        validarTransaccion(transaccion);
        
        // Verificar que el usuario existe
        if (!usuarioRepository.existsById(transaccion.getUsuario().getIdUsuario())) {
            throw new RuntimeException("El usuario especificado no existe");
        }

        // Asignar fecha automática si no se proporciona
        if (transaccion.getFecha() == null) {
            transaccion.setFecha(LocalDateTime.now());
        }
        
        // Asignar estado PENDIENTE por defecto si no se proporciona
        if (transaccion.getEstadoTransaccion() == null) {
            transaccion.setEstadoTransaccion("PENDIENTE");
        }

        return transaccionRepository.save(transaccion);
    }

    @Override
    public Transaccion actualizarTransaccion(Long id, Transaccion transaccion) {
        Transaccion transaccionExistente = obtenerTransaccionPorId(id);
        validarTransaccion(transaccion);

        // Validar que no se cambie el usuario
        if (!transaccionExistente.getUsuario().getIdUsuario().equals(transaccion.getUsuario().getIdUsuario())) {
            throw new RuntimeException("No se puede cambiar el usuario de la transacción");
        }

        // Actualizar solo los campos que se pueden modificar
        transaccionExistente.setTipo(transaccion.getTipo());
        transaccionExistente.setMonto(transaccion.getMonto());
        transaccionExistente.setMetodo(transaccion.getMetodo());
        transaccionExistente.setEstadoTransaccion(transaccion.getEstadoTransaccion());
        
        // Solo actualizar fecha si se proporciona una nueva
        if (transaccion.getFecha() != null) {
            transaccionExistente.setFecha(transaccion.getFecha());
        }

        return transaccionRepository.save(transaccionExistente);
    }

    @Override
    public Transaccion actualizarEstadoTransaccion(Long id, String estado) {
        Transaccion transaccion = obtenerTransaccionPorId(id);
        validarEstadoTransaccion(estado);
        
        transaccion.setEstadoTransaccion(estado);
        return transaccionRepository.save(transaccion);
    }

    @Override
    public void eliminarTransaccion(Long id) {
        if (!transaccionRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la transacción con ID: " + id);
        }
        transaccionRepository.deleteById(id);
    }

    private void validarTransaccion(Transaccion transaccion) {
        if (transaccion == null) {
            throw new RuntimeException("La transacción no puede ser nula");
        }
        if (transaccion.getUsuario() == null || transaccion.getUsuario().getIdUsuario() == null) {
            throw new RuntimeException("El usuario es requerido");
        }
        if (transaccion.getTipo() == null || transaccion.getTipo().trim().isEmpty()) {
            throw new RuntimeException("El tipo de transacción es requerido");
        }
        validarTipoTransaccion(transaccion.getTipo());
        
        if (transaccion.getMonto() == null) {
            throw new RuntimeException("El monto es requerido");
        }
        if (transaccion.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser mayor que cero");
        }
        
        if (transaccion.getMetodo() == null || transaccion.getMetodo().trim().isEmpty()) {
            throw new RuntimeException("El método de pago es requerido");
        }
        
        if (transaccion.getEstadoTransaccion() != null) {
            validarEstadoTransaccion(transaccion.getEstadoTransaccion());
        }
    }

    private void validarTipoTransaccion(String tipo) {
        if (!tipo.matches("(?i)^(DEPOSITO|RETIRO|APUESTA|GANANCIA)$")) {
            throw new RuntimeException("Tipo de transacción inválido. Debe ser: DEPOSITO, RETIRO, APUESTA o GANANCIA");
        }
    }

    private void validarEstadoTransaccion(String estado) {
        if (!estado.matches("(?i)^(PENDIENTE|COMPLETADA|CANCELADA|RECHAZADA)$")) {
            throw new RuntimeException("Estado de transacción inválido. Debe ser: PENDIENTE, COMPLETADA, CANCELADA o RECHAZADA");
        }
    }
}
