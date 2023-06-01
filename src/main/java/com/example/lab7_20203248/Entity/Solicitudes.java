package com.example.lab7_20203248.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "solicitudes")
public class Solicitudes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "solicitud_producto", length = 45)
    private String solicitud_producto;

    @Column(name = "solicitud_monto")
    private Double solicitud_monto;

    @Column(name = "solicitud_fecha")
    private LocalDate solicitud_fecha;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuarios usuarios_id;

    @Column(name = "solicitud_estado", length = 45)
    private String solicitud_estado;

}