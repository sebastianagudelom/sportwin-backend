package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cuota")
public class Cuota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCuota")
    private Long idCuota;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEvento", nullable = false)
    private EventoDeportivo evento;
    
    @Column(name = "descripcionCuota", nullable = false, length = 200)
    private String descripcionCuota;
    
    @Column(name = "valor_cuota", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorCuota;
    
    @Column(name = "estado_cuota", nullable = false, length = 20)
    @Builder.Default
    private String estadoCuota = "activa"; // activa/cerrada/suspendida
    
    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura;
    
    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;
    
    @Column(name = "fecha_actualizacion", nullable = false)
    @Builder.Default
    private LocalDateTime fechaActualizacion = LocalDateTime.now();
}
