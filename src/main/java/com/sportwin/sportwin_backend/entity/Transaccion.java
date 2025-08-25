package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data                   // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Constructor vacío
@AllArgsConstructor     // Constructor con todos los campos
@Builder                // Builder pattern para crear objetos fácilmente
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String tipo; // DEPOSITO, RETIRO, APUESTA, GANANCIA, PERDIDA

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String metodo; // PSE, TARJETA, PAYPAL

    @Column(nullable = false)
    private String estadoTransaccion; // PENDIENTE, COMPLETADA, FALLIDA, CANCELADA

    // Enums para los tipos de transacción
    public enum TipoTransaccion {
        DEPOSITO, RETIRO, APUESTA, GANANCIA, PERDIDA
    }

    // Enum para métodos de pago
    public enum MetodoPago {
        PSE, TARJETA, PAYPAL
    }

    // Enum para estados de transacción
    public enum EstadoTransaccion {
        PENDIENTE, COMPLETADA, FALLIDA, CANCELADA
    }
}
