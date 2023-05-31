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

        System.out.println("sol: "+solicitud.getSolicitud_fecha());

        HashMap<String, Object> responseMap = new HashMap<>();
        solicitudesRepository.guardarSolicitud(solicitud.getId(), solicitud.getSolicitud_producto(), solicitud.getSolicitud_monto(), solicitud.getSolicitud_fecha(), solicitud.getUsuarios_id().getId());

        responseMap.put("Monto solicitado", solicitud.getSolicitud_monto());
        responseMap.put("id", solicitud.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }
}
