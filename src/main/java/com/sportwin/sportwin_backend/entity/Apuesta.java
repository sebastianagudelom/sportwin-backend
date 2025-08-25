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
@Table(name = "apuesta")
public class Apuesta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idApuesta")
    private Long idApuesta;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCuota", nullable = false)
    private Cuota cuota;
    
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "estado_apuesta", nullable = false, length = 20)
    @Builder.Default
    private String estado = "pendiente"; // ganada, perdida, pendiente
    
    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estadoGeneral = "activa"; // activa/inactiva
    
    @Column(name = "fecha", nullable = false)
    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();
    
    @Column(name = "ganancia_potencial", precision = 10, scale = 2)
    private BigDecimal gananciaPotencial;
    
    @Column(name = "fecha_apuesta", nullable = false)
    @Builder.Default
    private LocalDateTime fechaApuesta = LocalDateTime.now();
    
    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
    
    // Método para calcular automáticamente la ganancia potencial
    @PrePersist
    @PreUpdate
    public void calcularGananciaPotencial() {
        try {
            if (this.monto != null && this.cuota != null && this.cuota.getValorCuota() != null) {
                this.gananciaPotencial = this.monto.multiply(this.cuota.getValorCuota());
            } else {
                // Si no se puede calcular, establecer en 0
                this.gananciaPotencial = BigDecimal.ZERO;
            }
        } catch (Exception e) {
            // En caso de error, establecer en 0
            this.gananciaPotencial = BigDecimal.ZERO;
        }
    }
}
