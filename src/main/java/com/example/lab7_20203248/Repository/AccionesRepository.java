package com.example.lab7_20203248.Repository;

import com.example.lab7_20203248.Entity.Acciones;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AccionesRepository extends JpaRepository<Acciones, Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO acciones (id, monto, fecha, usuarios_id) VALUES (?1, ?2, ?3, ?4)")
    void guardar(int id, Double monto, LocalDate fecha, int idUsuario);
}
