// ===== ENTIDAD COMPETICION =====
package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "competicion")
public class Competicion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompeticion")
    private Long idCompeticion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idDeporte", nullable = false)
    private Deporte deporte;
    
    @Column(name = "nombre_competicion", nullable = false, length = 100)
    private String nombreCompeticion;
    
    @Column(name = "lugar", length = 50)
    private String lugar;
    
    @Column(name = "temporadaActual", length = 20)
    private String temporadaActual; // ej: "2024-2025"
    
    @Column(name = "estado", nullable = false, length = 15)
    @Builder.Default
    private String estado = "activa"; // activa/finalizada/suspendida
    
    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fechaFin")
    private LocalDate fechaFin;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "logoUrl")
    private String logoUrl;

}