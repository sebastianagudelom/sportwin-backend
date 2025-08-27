package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Integer> {
    
    List<Promocion> findByEstado(Promocion.EstadoPromocion estado);
    
    Optional<Promocion> findByCodigoPromocional(String codigoPromocional);
    
    @Query("SELECT p FROM Promocion p WHERE p.fechaInicio <= :now AND p.fechaFin >= :now AND p.estado = 'activa'")
    List<Promocion> findActivePromotions(LocalDateTime now);
    
    List<Promocion> findBySoloNuevosUsuarios(Boolean soloNuevosUsuarios);
    
    @Query("SELECT p FROM Promocion p WHERE p.tipoPromocion = :tipoPromocion AND p.estado = 'activa'")
    List<Promocion> findActivePromotionsByType(Promocion.TipoPromocion tipoPromocion);
}
