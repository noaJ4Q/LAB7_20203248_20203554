package com.example.lab7_20203248.Repository;

import com.example.lab7_20203248.Entity.Usuarios;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO usuarios (id, nombre, apellido, correo, username, password, estado_logico, rol_id, fecha_registro) VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9)")
    void guardar(int id, String nombre, String apellido, String correo, String username, String password, String estado_logico, int rol_id, LocalDate fecha_registro);
}
