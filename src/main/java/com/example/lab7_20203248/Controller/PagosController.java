package com.example.lab7_20203248.Controller;

import com.example.lab7_20203248.Entity.Creditos;
import com.example.lab7_20203248.Entity.Pagos;
import com.example.lab7_20203248.Entity.Usuarios;
import com.example.lab7_20203248.Repository.CreditosRepository;
import com.example.lab7_20203248.Repository.PagosRepository;
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
@RequestMapping("/pagos")
public class PagosController {

    final PagosRepository pagosRepository;
    final UsuariosRepository usuariosRepository;
    final CreditosRepository creditosRepository;

    public PagosController(PagosRepository pagosRepository, UsuariosRepository usuariosRepository, CreditosRepository creditosRepository) {
        this.pagosRepository = pagosRepository;
        this.usuariosRepository = usuariosRepository;
        this.creditosRepository = creditosRepository;
    }

    @GetMapping("/listarPagos")
    public List<Pagos> listar(){
        return pagosRepository.findAll();
    }

    @PostMapping("/registrarPago")
    public ResponseEntity<HashMap<String, Object>> registrar(@RequestBody Pagos pago){//para raw por json

        HashMap<String, Object>  respuesta = new HashMap<>();
        List<Pagos> list = pagosRepository.findAll();
        List<Usuarios> lista = usuariosRepository.findAll();
        List<Creditos> creditos = creditosRepository.findAll();
        boolean checkUser = false;
        boolean checkCredit = false;
        boolean checkId = true;
        for (Pagos p: list){
            if(p.getId().equals(pago.getId())){
                checkId = false;
                break;
            }
        }
        for (Usuarios u: lista){
            if(u.getId() == pago.getUsuarios_id().getId()){
                checkUser = true;
                break;
            }
        }
        for (Creditos c: creditos){
            if(c.getId().equals(pago.getCreditos_id().getId())){
                checkCredit = true;
                break;
            }
        }

        if(!checkId || !checkUser || !checkCredit || pago.getMonto()==null){
            respuesta.put("result", "error");
            if(!checkId){
                respuesta.put("msg", "Debe ingresar una id nueva");
            }
            if(!checkUser){
                respuesta.put("msg", "Debe ingresar un usuario existente");
            }
            if (!checkCredit){
                respuesta.put("msg", "Debe ingresar un credito existente");
            }
            if(pago.getMonto()==null){
                respuesta.put("msg","Debe ingresar un monto para el pago");
            }
            return ResponseEntity.badRequest().body(respuesta);
        }
        pagosRepository.save(pago);
        respuesta.put("id", pago.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta); //para respetar RESTful hay un codigo por metodo

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
