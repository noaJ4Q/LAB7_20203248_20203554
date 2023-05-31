package com.example.lab7_20203248.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "creditos")
public class Creditos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "monto", length = 45)
    private String monto;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuarios usuarios_id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "interes")
    private Double interes;

}