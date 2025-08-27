package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PROMOCION")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Integer idPromocion;

    @Column(name = "nombre_promocion", length = 100, nullable = false)
    private String nombrePromocion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_promocion", nullable = false)
    private TipoPromocion tipoPromocion;

    @Column(name = "valor_promocion", precision = 10, scale = 2)
    private BigDecimal valorPromocion;

    @Column(name = "porcentaje_bono", precision = 5, scale = 2)
    private BigDecimal porcentajeBono;

    @Column(name = "monto_minimo_deposito", precision = 10, scale = 2)
    private BigDecimal montoMinimoDeposito;

    @Column(name = "monto_maximo_bono", precision = 10, scale = 2)
    private BigDecimal montoMaximoBono;

    @Column(name = "requisito_apuesta_multiple")
    private Integer requisitoApuestaMultiple;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "codigo_promocional", length = 20, unique = true)
    private String codigoPromocional;

    @Column(name = "max_usos_totales")
    private Integer maxUsosTotales;

    @Column(name = "usos_actuales")
    private Integer usosActuales;

    @Column(name = "solo_nuevos_usuarios")
    private Boolean soloNuevosUsuarios;

    @Column(name = "deportes_aplicables", columnDefinition = "json")
    private String deportesAplicables;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPromocion estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public enum TipoPromocion {
        bono_deposito,
        apuesta_gratis,
        cashback,
        multiplicador,
        bono_registro
    }

    public enum EstadoPromocion {
        activa,
        inactiva,
        agotada,
        expirada
    }

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        estado = estado == null ? EstadoPromocion.activa : estado;
        valorPromocion = valorPromocion == null ? BigDecimal.ZERO : valorPromocion;
        porcentajeBono = porcentajeBono == null ? BigDecimal.ZERO : porcentajeBono;
        montoMinimoDeposito = montoMinimoDeposito == null ? BigDecimal.ZERO : montoMinimoDeposito;
        montoMaximoBono = montoMaximoBono == null ? BigDecimal.ZERO : montoMaximoBono;
        requisitoApuestaMultiple = requisitoApuestaMultiple == null ? 1 : requisitoApuestaMultiple;
        maxUsosTotales = maxUsosTotales == null ? 0 : maxUsosTotales;
        usosActuales = usosActuales == null ? 0 : usosActuales;
        soloNuevosUsuarios = soloNuevosUsuarios == null ? false : soloNuevosUsuarios;
    }
}
