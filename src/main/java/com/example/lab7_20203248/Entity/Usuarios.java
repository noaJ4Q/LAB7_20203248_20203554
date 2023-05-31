package com.example.lab7_20203248.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String username;
    private String password;
    private String estado_logico;
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol_id;
    private LocalDateTime fecha_registro;
}