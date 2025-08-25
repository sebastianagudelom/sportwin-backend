package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tipo_apuesta")
public class TipoApuesta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoApuesta")
    private Long idTipoApuesta;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre; // ej: "Ganador del partido", "Total de goles", "Primer gol"
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "activa"; // activa/inactiva
    
    @Column(name = "tipoResultado", length = 50)
    private String tipoResultado; // ej: "ganador", "empate", "over/under", "ambos_marcan"
}
