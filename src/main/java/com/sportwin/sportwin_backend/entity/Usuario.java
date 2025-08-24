package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data                   // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Constructor vacío
@AllArgsConstructor     // Constructor con todos los campos
@Builder                // Builder pattern para crear objetos fácilmente
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    @Column(unique = true, nullable = false)
    private int cedula;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(unique = true, nullable = false)
    private String username;
    private String contrasena;

    private String estado; // Activo, Inactivo, Suspendido
    
    private String direccion;
    private String telefono;

    private BigDecimal saldo;
    private LocalDate fechaRegistro;
}