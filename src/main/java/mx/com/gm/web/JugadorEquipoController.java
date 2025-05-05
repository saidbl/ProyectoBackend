package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.JugadorEquipo;
import mx.com.gm.dto.JugadorEquipoDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.JugadorEquipoService;
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
public class JugadorEquipoController {
    
    @Autowired
    JugadorEquipoService jeservice;
    
    @GetMapping("/equipos/jugadores")
    public List<JugadorEquipo> list(@RequestParam Long id){
        return jeservice.listByIdEquipo(id);
    }
    @PostMapping("/equipos/vincular")
    public ResponseEntity<JugadorEquipo> agregarJugador(@RequestBody JugadorEquipoDTO rutina) {
        JugadorEquipo nuevaRutina = jeservice.add(rutina);
        return ResponseEntity.ok(nuevaRutina);
    }
    @DeleteMapping("/desasociar/{id}")
    public ResponseEntity<ResponseAPI>  desasociar(@PathVariable Long id){
        jeservice.delete(id);
        ResponseAPI response = new ResponseAPI();
        response.setMessage("Eliminado correctamente");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
    
}
