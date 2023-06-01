package com.example.lab7_20203248.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String username;
    private String password;
    private String estado_logico;
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol_id;
    private Instant fecha_registro;
}