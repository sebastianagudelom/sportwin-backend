package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "evento_deportivo")
public class EventoDeportivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvento")
    private Long idEvento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCompeticion", nullable = false)
    private Competicion competicion;
    
    @Column(name = "fechaEvento", nullable = false)
    private LocalDate fechaEvento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipoLocal", nullable = false)
    private Equipo equipoLocal;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipoVisitante", nullable = false)
    private Equipo equipoVisitante;
    
    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "pendiente"; // pendiente, en juego, finalizado
    
    @Column(name = "horaEvento")
    private LocalTime horaEvento;
    
    @Column(name = "resultadoLocal")
    private Integer resultadoLocal;
    
    @Column(name = "resultadoVisitante")
    private Integer resultadoVisitante;
}
