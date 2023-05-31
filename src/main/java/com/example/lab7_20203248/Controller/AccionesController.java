package com.example.lab7_20203248.Controller;

import com.example.lab7_20203248.Entity.Acciones;
import com.example.lab7_20203248.Repository.AccionesRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping(method = RequestMethod.GET, value = "/acciones")
public class AccionesController {

    final AccionesRepository accionesRepository;

    AccionesController(AccionesRepository accionesRepository){
        this.accionesRepository = accionesRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<HashMap<String, Object>> guardar(@RequestBody Acciones acciones){

        HashMap<String, Object> responesMap = new HashMap<>();
        accionesRepository.guardar(acciones.getId(), acciones.getMonto(), acciones.getFecha(), acciones.getUsuarios_id().getId());
        responesMap.put("idCreado", acciones.getUsuarios_id().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responesMap);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> gestionCrear(HttpServletRequest request){
        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST")){
            responseMap.put("estado", "error");
            responseMap.put("msg", "Acción nula");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

}
