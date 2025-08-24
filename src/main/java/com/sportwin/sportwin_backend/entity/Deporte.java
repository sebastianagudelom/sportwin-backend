package com.sportwin.sportwin_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deporte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_deporte;
    
    private String nombre_deporte;
    private String descripcion;
}
