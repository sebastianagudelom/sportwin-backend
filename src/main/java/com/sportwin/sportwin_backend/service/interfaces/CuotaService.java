package com.sportwin.sportwin_backend.service.interfaces;

import com.sportwin.sportwin_backend.entity.Cuota;
import java.util.List;

public interface CuotaService {
    
    // Obtener todas las cuotas
    List<Cuota> obtenerTodasLasCuotas();
    
    // Obtener una cuota por su ID
    Cuota obtenerCuotaPorId(Long id);
    
    // Crear una nueva cuota
    Cuota crearCuota(Cuota cuota);
    
    // Actualizar una cuota
    Cuota actualizarCuota(Cuota cuota);
    
    // Eliminar una cuota
    void eliminarCuota(Long id);
    
    // Obtener cuotas por evento deportivo
    List<Cuota> obtenerCuotasPorEvento(Long idEvento);
    
    // Obtener cuotas activas
    List<Cuota> obtenerCuotasActivas();
    
    // Cerrar cuota
    Cuota cerrarCuota(Long id);
    
    // Suspender cuota
    Cuota suspenderCuota(Long id);
}
