package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Usuario;
import com.sportwin.sportwin_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos los usuarios
    @GetMapping // http://localhost:8080/api/usuarios
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    @GetMapping("/{id}") // http://localhost:8080/api/usuarios/{id}
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Crear usuario
    @PostMapping // http://localhost:8080/api/usuarios
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        usuario.setFechaRegistro(LocalDate.now()); // asigna fecha automática
        return usuarioRepository.save(usuario);
    }

    // Actualizar usuario
    @PutMapping("/{id}") // http://localhost:8080/api/usuarios/{id}
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioExistente.setCedula(usuario.getCedula());
            usuarioExistente.setCorreo(usuario.getCorreo());
            usuarioExistente.setUsername(usuario.getUsername());
            usuarioExistente.setContrasena(usuario.getContrasena());
            usuarioExistente.setEstado(usuario.getEstado());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setSaldo(usuario.getSaldo());
            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }

    // Eliminar usuario
    @DeleteMapping("/{id}") // http://localhost:8080/api/usuarios/{id}
    public String deleteUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "Usuario con id " + id + " eliminado correctamente.";
    }

    // Endpoint de prueba
    @GetMapping("/test")
    public String test() {
        return "UsuarioController test endpoint working!";
    }
}