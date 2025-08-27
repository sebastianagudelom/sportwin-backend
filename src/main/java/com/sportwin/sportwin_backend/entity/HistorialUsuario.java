package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORIAL_USUARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "total_apostado", precision = 10, scale = 2)
    private BigDecimal totalApostado;

    @Column(name = "total_ganado", precision = 10, scale = 2)
    private BigDecimal totalGanado;

    @Column(name = "total_perdido", precision = 10, scale = 2)
    private BigDecimal totalPerdido;

    @Column(name = "numero_apuestas_ganadas")
    private Integer numeroApuestasGanadas;

    @Column(name = "numero_apuestas_perdidas")
    private Integer numeroApuestasPerdidas;

    @Column(name = "numero_apuestas_totales")
    private Integer numeroApuestasTotales;

    @Column(name = "porcentaje_acierto", precision = 5, scale = 2)
    private BigDecimal porcentajeAcierto;

    @Column(name = "racha_actual")
    private Integer rachaActual;

    @Column(name = "mejor_racha_ganadora")
    private Integer mejorRachaGanadora;

    @Column(name = "apuesta_mayor_ganancia", precision = 10, scale = 2)
    private BigDecimal apuestaMayorGanancia;

    @ManyToOne
    @JoinColumn(name = "deporte_preferido")
    private Deporte deportePreferido;

    @Column(name = "fecha_ultima_apuesta")
    private LocalDateTime fechaUltimaApuesta;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        totalApostado = totalApostado == null ? BigDecimal.ZERO : totalApostado;
        totalGanado = totalGanado == null ? BigDecimal.ZERO : totalGanado;
        totalPerdido = totalPerdido == null ? BigDecimal.ZERO : totalPerdido;
        numeroApuestasGanadas = numeroApuestasGanadas == null ? 0 : numeroApuestasGanadas;
        numeroApuestasPerdidas = numeroApuestasPerdidas == null ? 0 : numeroApuestasPerdidas;
        numeroApuestasTotales = numeroApuestasTotales == null ? 0 : numeroApuestasTotales;
        porcentajeAcierto = porcentajeAcierto == null ? BigDecimal.ZERO : porcentajeAcierto;
        rachaActual = rachaActual == null ? 0 : rachaActual;
        mejorRachaGanadora = mejorRachaGanadora == null ? 0 : mejorRachaGanadora;
        apuestaMayorGanancia = apuestaMayorGanancia == null ? BigDecimal.ZERO : apuestaMayorGanancia;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
