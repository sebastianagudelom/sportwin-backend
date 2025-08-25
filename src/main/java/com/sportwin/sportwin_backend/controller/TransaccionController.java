package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Transaccion;

import com.sportwin.sportwin_backend.repository.TransaccionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionRepository transaccionRepository;

    // Listar todas las transacciones
    @GetMapping // http://localhost:8080/api/transacciones
    public List<Transaccion> getTransacciones() {
        return transaccionRepository.findAll();
    }

    // Buscar transacción por ID
    @GetMapping("/{id}") // http://localhost:8080/api/transacciones/{id}
    public Transaccion getTransaccionById(@PathVariable Long id) {
        return transaccionRepository.findById(id).orElse(null);
    }

    // Buscar transacciones por usuario
    @GetMapping("/usuario/{idUsuario}") // http://localhost:8080/api/transacciones/usuario/{idUsuario}
    public List<Transaccion> getTransaccionesByUsuario(@PathVariable Long idUsuario) {
        return transaccionRepository.findByUsuarioIdUsuario(idUsuario);
    }

    // Buscar transacciones por tipo
    @GetMapping("/tipo/{tipo}") // http://localhost:8080/api/transacciones/tipo/{tipo}
    public List<Transaccion> getTransaccionesByTipo(@PathVariable String tipo) {
        return transaccionRepository.findByTipo(tipo);
    }

    // Buscar transacciones por estado
    @GetMapping("/estado/{estado}") // http://localhost:8080/api/transacciones/estado/{estado}
    public List<Transaccion> getTransaccionesByEstado(@PathVariable String estado) {
        return transaccionRepository.findByEstadoTransaccion(estado);
    }

    // Crear transacción
    @PostMapping // http://localhost:8080/api/transacciones
    public Transaccion createTransaccion(@RequestBody Transaccion transaccion) {
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

    // Actualizar transacción
    @PutMapping("/{id}") // http://localhost:8080/api/transacciones/{id}
    public Transaccion updateTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccion) {
        Transaccion transaccionExistente = transaccionRepository.findById(id).orElse(null);
        if (transaccionExistente != null) {
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
        return null;
    }

    // Actualizar estado de transacción
    @PatchMapping("/{id}/estado") // http://localhost:8080/api/transacciones/{id}/estado
    public Transaccion updateEstadoTransaccion(@PathVariable Long id, @RequestBody String estado) {
        Transaccion transaccion = transaccionRepository.findById(id).orElse(null);
        if (transaccion != null) {
            transaccion.setEstadoTransaccion(estado);
            return transaccionRepository.save(transaccion);
        }
        return null;
    }

    // Eliminar transacción
    @DeleteMapping("/{id}") // http://localhost:8080/api/transacciones/{id}
    public String deleteTransaccion(@PathVariable Long id) {
        transaccionRepository.deleteById(id);
        return "Transacción con id " + id + " eliminada correctamente.";
    }
}
