package com.example.lab7_20203248.Repository;

import com.example.lab7_20203248.Entity.Solicitudes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface SolicitudesRepository extends JpaRepository<Solicitudes, Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO solicitudes (id, solicitud_producto, solicitud_monto, solicitud_fecha, usuarios_id, solicitud_estado) \n" +
            "VALUES (?1, ?2, ?3, ?4, ?5, 'pendiente')")
    void guardarSolicitud(int id, String solicitudProducto, Double solicitudMonto, Instant solicitudFecha, int usuariosId);

    @Query(nativeQuery = true, value = "SELECT solicitud_estado FROM solicitudes WHERE id = ?1")
    String verEstado(int idSolicitud);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE solicitudes SET solicitud_estado = 'aprobada' WHERE (id = ?1)")
    void aprobarSolicitud(int idSolicitud);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE solicitudes SET solicitud_estado = 'denegada' WHERE (id = ?1)")
    void denegarSolicitud(int idSolicitud);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM solicitudes WHERE id = ?1")
    void borrarSolicitud(int idSolicitud);
}
