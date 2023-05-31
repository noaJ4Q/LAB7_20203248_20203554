package com.example.lab7_20203248.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuotas")
public class Cuotas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "numero_cuota")
    private Integer numero_cuota;

    @Column(name = "monto")
    private Double monto;

    @ManyToOne
    @JoinColumn(name = "creditos_id", nullable = false)
    private Creditos creditos_id;

}