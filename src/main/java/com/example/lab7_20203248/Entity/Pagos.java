package com.example.lab7_20203248.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "pagos")
public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "monto")
    private Double monto;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuarios usuarios_id;

    @Column(name = "tipo_pago", length = 45)
    private String tipo_pago;

    @Column(name = "fecha")
    private Instant fecha;

    @ManyToOne
    @JoinColumn(name = "creditos_id", nullable = false)
    private Creditos creditos_id;

}