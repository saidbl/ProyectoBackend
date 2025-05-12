package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.RutinaJugador;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.RutinaJugadorDTO;
import mx.com.gm.service.RutinaJugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RutinaJugadorController {
    
    @Autowired
    RutinaJugadorService rservice;
     @GetMapping("/rutinaJugador")
    public List<RutinaJugador> list(@RequestParam Long id){
        System.out.println(id);
        return rservice.listbyJugadorId(id);
    }
    @PostMapping("/rutinaJugador/vincular")
    public ResponseEntity<RutinaJugador> agregarRutina(@RequestBody RutinaJugadorDTO rutina) {
        RutinaJugador nuevaRutina = rservice.add(rutina);
        return ResponseEntity.ok(nuevaRutina);
    }
    @DeleteMapping("rutinaJugador/desvincular/{id}")
    public ResponseEntity<ResponseAPI> eliminarVinculo(@PathVariable Long id){
        rservice.delete(id);
        ResponseAPI rapi = new ResponseAPI();
        rapi.setMessage("Eliminado correctamente");
        rapi.setSuccess(true);
        return ResponseEntity.ok(rapi);
    }
}
