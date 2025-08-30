package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.Promocion;
import java.util.List;

public interface PromocionService {

    // Obtener todas las promociones
    List<Promocion> obtenerTodasLasPromociones();
    
    // Obtener una promoción por su ID
    Promocion obtenerPromocionPorId(Integer id);
    
    // Obtener promociones activas
    List<Promocion> obtenerPromocionesActivas();
    
    // Obtener una promoción por su código
    Promocion obtenerPromocionPorCodigo(String codigo);
    
    // Obtener promociones por tipo
    List<Promocion> obtenerPromocionesPorTipo(Promocion.TipoPromocion tipo);
    
    // Obtener promociones por estado
    List<Promocion> obtenerPromocionesPorEstado(Promocion.EstadoPromocion estado);
    
    // Obtener promociones por tipo de usuario
    List<Promocion> obtenerPromocionesPorTipoUsuario(Boolean soloNuevos);
    
    // Crear una nueva promoción
    Promocion crearPromocion(Promocion promocion);
    
    // Actualizar una promoción existente
    Promocion actualizarPromocion(Integer id, Promocion promocion);

    // Eliminar una promoción
    void eliminarPromocion(Integer id);
    
    // Incrementar el uso de una promoción
    Promocion incrementarUsoPromocion(Integer id);
}
