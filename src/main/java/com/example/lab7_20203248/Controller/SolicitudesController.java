package com.example.lab7_20203248.Controller;

import com.example.lab7_20203248.Entity.Solicitudes;
import com.example.lab7_20203248.Repository.SolicitudesRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping(method = RequestMethod.GET, value = "/solicitudes")
public class SolicitudesController {

    final SolicitudesRepository solicitudesRepository;

    SolicitudesController(SolicitudesRepository solicitudesRepository){
        this.solicitudesRepository = solicitudesRepository;
    }

    @PostMapping("/registro")
    public ResponseEntity<HashMap<String, Object>> guardar(@RequestBody Solicitudes solicitud){

        HashMap<String, Object> responseMap = new HashMap<>();
        solicitudesRepository.guardarSolicitud(solicitud.getId(), solicitud.getSolicitud_producto(), solicitud.getSolicitud_monto(), solicitud.getSolicitud_fecha(), solicitud.getUsuarios_id().getId());

        responseMap.put("Monto solicitado", solicitud.getSolicitud_monto());
        responseMap.put("id", solicitud.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }

    @PutMapping("/aprobarSolicitud")
    public ResponseEntity<HashMap<String, Object>> aprobar(@RequestParam("idSolicitud") Integer idSolicitud){

        HashMap<String, Object> responseMap = new HashMap<>();

        if (idSolicitud != null){
            String estado = solicitudesRepository.verEstado(idSolicitud);
            if (estado.equals("pendiente")){
                solicitudesRepository.aprobarSolicitud(idSolicitud);
                responseMap.put("id solicitud", idSolicitud);
                return ResponseEntity.ok(responseMap);
            }
            else {
                responseMap.put("solicitud ya atendida", idSolicitud);
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseMap);
            }
        }
        else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "ID nulo");
        }

        return ResponseEntity.badRequest().body(responseMap);

    }

    @PutMapping("/denegarSolicitud")
    public ResponseEntity<HashMap<String, Object>> denegar(@RequestParam("idSolicitud") Integer idSolicitud){

        HashMap<String, Object> responseMap = new HashMap<>();

        if (idSolicitud != null){
            String estado = solicitudesRepository.verEstado(idSolicitud);
            if (estado.equals("pendiente")){
                solicitudesRepository.denegarSolicitud(idSolicitud);
                responseMap.put("id solicitud", idSolicitud);
                return ResponseEntity.ok(responseMap);
            }
            else {
                responseMap.put("solicitud ya atendida", idSolicitud);
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseMap);
            }

        }
        else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "ID nulo");
        }

        return ResponseEntity.badRequest().body(responseMap);

    }

    @DeleteMapping("borrarSolicitud")
    public ResponseEntity<HashMap<String, Object>> borrar(@RequestParam("idSolicitud") Integer idSolicitud){

        HashMap<String, Object> responseMap = new HashMap<>();

        if (idSolicitud != null){
            String estado = solicitudesRepository.verEstado(idSolicitud);
            if (estado.equals("denegada")){
                solicitudesRepository.borrarSolicitud(idSolicitud);
                responseMap.put("id solicitud borrada", idSolicitud);
                return ResponseEntity.ok(responseMap);
            }
            else {
                responseMap.put("solicitud no encontrada", idSolicitud);
                return ResponseEntity.badRequest().body(responseMap);
            }
        }
        else{
            responseMap.put("estado", "error");
            responseMap.put("msg", "ID nulo");
        }

        return ResponseEntity.badRequest().body(responseMap);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> gestionCrear(HttpServletRequest request){
        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT") || request.getMethod().equals("DELETE")){
            responseMap.put("estado", "error");
            responseMap.put("msg", "Parámetro(s) nulos");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

}
