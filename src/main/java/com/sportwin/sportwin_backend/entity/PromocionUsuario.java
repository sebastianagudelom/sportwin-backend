package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PROMOCION_USUARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromocionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion_usuario")
    private Integer idPromocionUsuario;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_promocion", nullable = false)
    private Promocion promocion;

    @Column(name = "fecha_activacion")
    private LocalDateTime fechaActivacion;

    @Column(name = "monto_bono_otorgado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoBonoOtorgado;

    @Column(name = "monto_apostado_requerido", precision = 10, scale = 2)
    private BigDecimal montoApostadoRequerido;

    @Column(name = "monto_apostado_actual", precision = 10, scale = 2)
    private BigDecimal montoApostadoActual;

    @Column(name = "numero_apuestas_requeridas")
    private Integer numeroApuestasRequeridas;

    @Column(name = "numero_apuestas_realizadas")
    private Integer numeroApuestasRealizadas;

    @Column(name = "codigo_usado", length = 20)
    private String codigoUsado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_promocion")
    private EstadoPromocionUsuario estadoPromocion;

    @Column(name = "fecha_completacion")
    private LocalDateTime fechaCompletacion;

    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;

    @Column(name = "notas_adicionales", columnDefinition = "TEXT")
    private String notasAdicionales;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public enum EstadoPromocionUsuario {
        activa,
        completada,
        expirada,
        cancelada
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        fechaCreacion = now;
        fechaActualizacion = now;
        fechaActivacion = fechaActivacion == null ? now : fechaActivacion;
        estadoPromocion = estadoPromocion == null ? EstadoPromocionUsuario.activa : estadoPromocion;
        montoApostadoRequerido = montoApostadoRequerido == null ? BigDecimal.ZERO : montoApostadoRequerido;
        montoApostadoActual = montoApostadoActual == null ? BigDecimal.ZERO : montoApostadoActual;
        numeroApuestasRequeridas = numeroApuestasRequeridas == null ? 0 : numeroApuestasRequeridas;
        numeroApuestasRealizadas = numeroApuestasRealizadas == null ? 0 : numeroApuestasRealizadas;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
