package com.example.lab7_20203248.Controller;

import com.example.lab7_20203248.Entity.Acciones;
import com.example.lab7_20203248.Entity.Usuarios;
import com.example.lab7_20203248.Repository.AccionesRepository;
import com.example.lab7_20203248.Repository.UsuariosRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(method = RequestMethod.GET, value = "/acciones")
public class AccionesController {

    final AccionesRepository accionesRepository;
    final UsuariosRepository usuariosRepository;

    AccionesController(AccionesRepository accionesRepository, UsuariosRepository usuariosRepository){
        this.accionesRepository = accionesRepository;
        this.usuariosRepository = usuariosRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<HashMap<String, Object>> guardar(@RequestBody Acciones acciones){

        HashMap<String, Object> responesMap = new HashMap<>();
        List<Acciones> list = accionesRepository.findAll();
        List<Usuarios> list1 = usuariosRepository.findAll();

        boolean checkAccion = true;
        for (Acciones a: list){
            if(a.getId().equals(acciones.getId())){
                checkAccion = false;
                break;
            }
        }

        boolean checkUser = false;
        for (Usuarios u: list1){
            if (u.getId() == acciones.getUsuarios_id().getId()){
                checkUser = true;
                break;
            }
        }
        if (!checkUser || !checkAccion || acciones.getMonto()==null || acciones.getFecha()==null){
            responesMap.put("result", "error");
            if (!checkUser){
                responesMap.put("msg","Debe ingresar un usuario existente");
            }
            if (!checkAccion){
                responesMap.put("msg","Debe ingresar una accion existente");
            }
            if (acciones.getMonto()==null){
                responesMap.put("msg","Se requiere un monto");
            }
            if (acciones.getFecha()==null){
                responesMap.put("msg","Se requiere una fecha");
            }
        }

        accionesRepository.save(acciones);
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
