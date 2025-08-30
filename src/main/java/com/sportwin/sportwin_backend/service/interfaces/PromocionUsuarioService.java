package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.PromocionUsuario;
import java.util.List;
import java.math.BigDecimal;

public interface PromocionUsuarioService {

    // Obtener todas las promociones de un usuario
    List<PromocionUsuario> obtenerTodasLasPromocionesUsuario();

    // Obtener una promoción de un usuario por el ID de la promoción
    PromocionUsuario obtenerPromocionUsuarioPorId(Integer id);

    // Obtener promociones de un usuario por su ID
    List<PromocionUsuario> obtenerPromocionesUsuarioPorUsuario(Long idUsuario);

    // Obtener promociones activas de un usuario
    List<PromocionUsuario> obtenerPromocionesActivasPorUsuario(Long idUsuario);

    // Obtener promociones de un usuario por el ID de la promoción
    List<PromocionUsuario> obtenerPromocionesUsuarioPorPromocion(Integer idPromocion);

    // Obtener promociones de un usuario por su estado
    List<PromocionUsuario> obtenerPromocionesUsuarioPorEstado(PromocionUsuario.EstadoPromocionUsuario estado);

    // Crear una nueva promoción para un usuario
    PromocionUsuario crearPromocionUsuario(PromocionUsuario promocionUsuario);

    // Actualizar una promoción de un usuario
    PromocionUsuario actualizarPromocionUsuario(Integer id, PromocionUsuario promocionUsuario);

    // Eliminar una promoción de un usuario
    void eliminarPromocionUsuario(Integer id);

    // Actualizar el progreso de una promoción de un usuario
    PromocionUsuario actualizarProgreso(Integer id, Integer apuestasRealizadas, BigDecimal montoApostado);

    // Cancelar una promoción de un usuario
    PromocionUsuario cancelarPromocion(Integer id);
}
