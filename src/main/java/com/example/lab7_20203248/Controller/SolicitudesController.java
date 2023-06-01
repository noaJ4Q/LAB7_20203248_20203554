package com.example.lab7_20203248.Controller;

import com.example.lab7_20203248.Entity.Solicitudes;
import com.example.lab7_20203248.Entity.Usuarios;
import com.example.lab7_20203248.Repository.SolicitudesRepository;
import com.example.lab7_20203248.Repository.UsuariosRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping(method = RequestMethod.GET, value = "/solicitudes")
public class SolicitudesController {

    final SolicitudesRepository solicitudesRepository;
    final UsuariosRepository usuariosRepository;

    SolicitudesController(SolicitudesRepository solicitudesRepository, UsuariosRepository usuariosRepository){
        this.solicitudesRepository = solicitudesRepository;
        this.usuariosRepository = usuariosRepository;
    }

    @PostMapping("/registro")
    public ResponseEntity<HashMap<String, Object>> guardar(@RequestBody Solicitudes solicitud){

        HashMap<String, Object> responseMap = new HashMap<>();
        List<Solicitudes> list = solicitudesRepository.findAll();
        List<Usuarios> list1 = usuariosRepository.findAll();
        boolean checkId = true;
        HashMap<String, Object> hashMap = new HashMap<>();
        for (Solicitudes s: list){
            if(Objects.equals(solicitud.getId(), s.getId())){
                checkId = false;
                break;
            }
        }
        boolean checkUser = false;
        for (Usuarios u: list1){
            if(u.getId() == solicitud.getUsuarios_id().getId()){
                checkUser = true;
                break;
            }
        }
        if(!checkId || !checkUser || !solicitud.getSolicitud_estado().equals("") || solicitud.getSolicitud_producto()==null || solicitud.getSolicitud_monto()==null || solicitud.getSolicitud_fecha()==null){
            hashMap.put("result", "error");
            if(!checkId){
                hashMap.put("msg","Debe ingresar un id nuevo o borrar el atributo para generar uno válido");
            }
            if (!checkUser){
                hashMap.put("msg","Debe ingresar un id de usuario válido");
            }
            if(!solicitud.getSolicitud_estado().equals("") && !solicitud.getSolicitud_estado().equals(" ")){
                hashMap.put("msg","Debe ingresar el atributo 'solicitud_estado' sin contenido, es decir: ''");
            }
            if (solicitud.getSolicitud_fecha()==null){
                hashMap.put("msg","Debe ingresar una fecha");
            }
            if (solicitud.getSolicitud_monto()==null){
                hashMap.put("msg","Debe ingresar un monto");
            }
            if(solicitud.getSolicitud_producto()==null){
                hashMap.put("msg","Debe ingresar un nombre de producto");
            }
            return ResponseEntity.badRequest().body(hashMap);
        }
        solicitud.setSolicitud_estado("pendiente");
        solicitudesRepository.save(solicitud);

        responseMap.put("Monto solicitado", solicitud.getSolicitud_monto());
        responseMap.put("id", solicitud.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }

    @PutMapping("/aprobarSolicitud")
    public ResponseEntity<HashMap<String, Object>> aprobar(@RequestParam("idSolicitud") Integer idSolicitud){

        HashMap<String, Object> responseMap = new HashMap<>();
        List<Solicitudes> list = solicitudesRepository.findAll();
        boolean check = false;
        for (Solicitudes s: list){
            if(Objects.equals(s.getId(), idSolicitud)){
                check = true;
                break;
            }
        }
        if (check){
            String estado = solicitudesRepository.verEstado(idSolicitud);
            if (estado.equals("pendiente")){
                solicitudesRepository.aprobarSolicitud(idSolicitud);
                responseMap.put("id solicitud", idSolicitud);
                return ResponseEntity.ok(responseMap);
            }
            else {
                responseMap.put("result", "alert");
                responseMap.put("msg", "solicitud ya atendida");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseMap);
            }
        }
        else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "ID nulo o inexistente");
        }

        return ResponseEntity.badRequest().body(responseMap);

    }

    @PutMapping("/denegarSolicitud")
    public ResponseEntity<HashMap<String, Object>> denegar(@RequestParam("idSolicitud") Integer idSolicitud){

        HashMap<String, Object> responseMap = new HashMap<>();
        List<Solicitudes> list = solicitudesRepository.findAll();
        boolean check = false;
        for (Solicitudes s: list){
            if(Objects.equals(s.getId(), idSolicitud)){
                check = true;
                break;
            }
        }

        if (check){
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
            responseMap.put("msg", "ID nulo o inexistente");
        }

        return ResponseEntity.badRequest().body(responseMap);

    }

    @DeleteMapping("borrarSolicitud")
    public ResponseEntity<HashMap<String, Object>> borrar(@RequestParam("idSolicitud") Integer idSolicitud){

        HashMap<String, Object> responseMap = new HashMap<>();
        List<Solicitudes> list = solicitudesRepository.findAll();
        boolean check = false;
        for (Solicitudes s: list){
            if(Objects.equals(s.getId(), idSolicitud)){
                check = true;
                break;
            }
        }

        if (check){
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
