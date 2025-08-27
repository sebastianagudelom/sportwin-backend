package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.PromocionUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PromocionUsuarioRepository extends JpaRepository<PromocionUsuario, Integer> {
    
    List<PromocionUsuario> findByUsuario_IdUsuario(Integer idUsuario);
    
    List<PromocionUsuario> findByPromocion_IdPromocion(Integer idPromocion);
    
    List<PromocionUsuario> findByEstadoPromocion(PromocionUsuario.EstadoPromocionUsuario estado);
    
    @Query("SELECT pu FROM PromocionUsuario pu WHERE pu.usuario.idUsuario = :idUsuario AND pu.estadoPromocion = 'activa'")
    List<PromocionUsuario> findActivePromotionsByUser(Integer idUsuario);
    
    @Query("SELECT pu FROM PromocionUsuario pu WHERE pu.usuario.idUsuario = :idUsuario AND pu.promocion.idPromocion = :idPromocion")
    List<PromocionUsuario> findByUsuarioAndPromocion(Integer idUsuario, Integer idPromocion);
    
    List<PromocionUsuario> findByCodigoUsado(String codigoUsado);
}
