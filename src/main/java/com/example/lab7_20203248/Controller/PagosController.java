package com.example.lab7_20203248.Controller;

import com.example.lab7_20203248.Entity.Pagos;
import com.example.lab7_20203248.Repository.PagosRepository;
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

    public PagosController(PagosRepository pagosRepository) {
        this.pagosRepository = pagosRepository;
    }

    @GetMapping("/listarPagos")
    public List<Pagos> listar(){
        return pagosRepository.findAll();
    }

    @PostMapping("/registrarPago")
    public ResponseEntity<HashMap<String, Object>> registrar(@RequestBody Pagos pago){//para raw por json

        pagosRepository.save(pago);
        HashMap<String, Object>  respuesta = new HashMap<>();
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
