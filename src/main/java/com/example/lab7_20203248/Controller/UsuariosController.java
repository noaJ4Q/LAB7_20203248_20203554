package com.example.lab7_20203248.Controller;

import com.example.lab7_20203248.Entity.Rol;
import com.example.lab7_20203248.Entity.Usuarios;
import com.example.lab7_20203248.Repository.RolRepository;
import com.example.lab7_20203248.Repository.UsuariosRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(method = RequestMethod.GET, value = "/users")
public class UsuariosController {

    final UsuariosRepository usuariosRepository;
    final RolRepository rolRepository;

    UsuariosController(UsuariosRepository usuariosRepository, RolRepository rolRepository){
        this.usuariosRepository = usuariosRepository;
        this.rolRepository = rolRepository;
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE+"; charset=utf-8")
    public List<Usuarios> listar(){
        return usuariosRepository.findAll();
    }

    @PostMapping(value = "/crear")
    public ResponseEntity<HashMap<String, Object>> crear(@RequestBody Usuarios usuarios){

        HashMap<String, Object> responseMap = new HashMap<>();
        List<Usuarios> lista = usuariosRepository.findAll();
        List<Rol> listaR = rolRepository.findAll();
        boolean checkId = true;
        boolean checkRol = true;
        for (Usuarios u: lista){
            if(u.getId()==usuarios.getId()){
                checkId = false;
                break;
            }
        }
        int contador = 0;
        for (Rol r: listaR){
            if(r.getId().equals(usuarios.getRol_id().getId())){
                if(!r.getNombre_rol().equals(usuarios.getRol_id().getNombre_rol())) {
                    checkRol = false;
                }
                contador ++;
                break;
            }
        }
        if(!checkId || usuarios.getRol_id()==null || usuarios.getRol_id().getId()==null || !checkRol){
            responseMap.put("result", "error");
            if(!checkId) {
                responseMap.put("msg", "Ingrese un id válido");
            }
            if(usuarios.getRol_id()==null || !checkRol || usuarios.getRol_id().getId()==null){
                responseMap.put("msg", "Ingrese un rol válido");
            }
            return ResponseEntity.badRequest().body(responseMap);
        }
        if(contador == 0){
            rolRepository.save(usuarios.getRol_id());
        }
        //usuariosRepository.guardar(usuarios.getId(), usuarios.getNombre(), usuarios.getApellido(), usuarios.getCorreo(), usuarios.getUsername(), usuarios.getPassword(), usuarios.getEstado_logico(), usuarios.getRol_id().getId(), usuarios.getFecha_registro());
        usuariosRepository.save(usuarios);
        responseMap.put("id creado:", usuarios.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> gestionCrear(HttpServletRequest request){
        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST")){
            responseMap.put("estado", "error");
            responseMap.put("msg", "Usuario nulo");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

}
