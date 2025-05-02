package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.RegistroRendimiento;
import mx.com.gm.dto.RegistroRendimientoDTO;
import mx.com.gm.service.RegistroRendimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistroRendimientoController {
    @Autowired 
    RegistroRendimientoService rrservice;
    
    @PostMapping("/agregarRecord")
    public ResponseEntity<RegistroRendimiento>add(@RequestBody RegistroRendimientoDTO rrdto){
        RegistroRendimiento r = rrservice.add(rrdto);
        return ResponseEntity.ok(r);
    }
    @GetMapping("/recordsJugador/{id}")
    public List<RegistroRendimiento>listar(@PathVariable Long id){
        return rrservice.getRendimientoDeportista(id);
    }
    
}
