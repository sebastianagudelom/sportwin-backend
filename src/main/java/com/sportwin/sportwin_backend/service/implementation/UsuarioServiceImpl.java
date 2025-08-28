package com.sportwin.sportwin_backend.service.implementation;

import com.sportwin.sportwin_backend.entity.Usuario;
import com.sportwin.sportwin_backend.repository.UsuarioRepository;
import com.sportwin.sportwin_backend.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        // Validar campos requeridos
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new RuntimeException("El correo es requerido");
        }
        if (usuario.getCedula() == 0) {
            throw new RuntimeException("La cédula es requerida");
        }
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new RuntimeException("El username es requerido");
        }
        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es requerida");
        }

        // Verificar si ya existe un usuario con el mismo correo o cédula
        if (usuarioRepository.findByCorreo(usuario.getCorreo()) != null) {
            throw new RuntimeException("Ya existe un usuario con este correo");
        }

        // Establecer valores por defecto
        usuario.setFechaRegistro(LocalDate.now());
        if (usuario.getEstado() == null) {
            usuario.setEstado("activo");
        }
        if (usuario.getSaldo() == null) {
            usuario.setSaldo(BigDecimal.ZERO);
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con correo: " + correo);
        }
        return usuario;
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        if (usuario.getIdUsuario() == null) {
            throw new RuntimeException("No se puede actualizar un usuario sin ID");
        }
        
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuario.getIdUsuario()));
        
        // Actualizar campos si no son null
        if (usuario.getNombre() != null) usuarioExistente.setNombre(usuario.getNombre());
        if (usuario.getApellido() != null) usuarioExistente.setApellido(usuario.getApellido());
        if (usuario.getFechaNacimiento() != null) usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
        if (usuario.getCedula() != 0) usuarioExistente.setCedula(usuario.getCedula());
        if (usuario.getCorreo() != null) usuarioExistente.setCorreo(usuario.getCorreo());
        if (usuario.getUsername() != null) usuarioExistente.setUsername(usuario.getUsername());
        if (usuario.getContrasena() != null) usuarioExistente.setContrasena(usuario.getContrasena());
        if (usuario.getEstado() != null) usuarioExistente.setEstado(usuario.getEstado());
        if (usuario.getDireccion() != null) usuarioExistente.setDireccion(usuario.getDireccion());
        if (usuario.getTelefono() != null) usuarioExistente.setTelefono(usuario.getTelefono());
        if (usuario.getSaldo() != null) usuarioExistente.setSaldo(usuario.getSaldo());
        
        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
