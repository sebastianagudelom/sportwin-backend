package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.Usuario;
import java.util.List;

public interface UsuarioService {
    
    // Crear un nuevo usuario
    Usuario crearUsuario(Usuario usuario);
    
    // Obtener un usuario por su ID
    Usuario obtenerUsuarioPorId(Long id);
    
    // Obtener un usuario por su correo
    Usuario obtenerUsuarioPorCorreo(String correo);
    
    // Obtener todos los usuarios
    List<Usuario> obtenerTodosLosUsuarios();
    
    // Actualizar un usuario existente
    Usuario actualizarUsuario(Usuario usuario);
    
    // Eliminar un usuario
    void eliminarUsuario(Long id);
}
