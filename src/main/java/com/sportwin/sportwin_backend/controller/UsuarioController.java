package com.sportwin.sportwin_backend.controller;

import com.sportwin.sportwin_backend.entity.Usuario;
import com.sportwin.sportwin_backend.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar usuario por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> getUsuarioByCorreo(@PathVariable String correo) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correo);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear usuario
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.ok(usuarioCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear el usuario: " + e.getMessage());
        }
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            usuario.setIdUsuario(id);
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}