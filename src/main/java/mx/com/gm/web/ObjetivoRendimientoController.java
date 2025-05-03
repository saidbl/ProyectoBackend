package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.ObjetivoRendimiento;
import mx.com.gm.dto.ObjetivoRendimientoDTO;
import mx.com.gm.dto.ProgresoObjetivoDTO;
import mx.com.gm.service.ObjetivoRendimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ObjetivoRendimientoController {
    @Autowired
    ObjetivoRendimientoService orservice;
    
    @PostMapping("/agregarGoal")
    public ResponseEntity<ObjetivoRendimiento>add(@RequestBody ObjetivoRendimientoDTO rrdto){
        ObjetivoRendimiento r = orservice.add(rrdto);
        return ResponseEntity.ok(r);
    }
    @GetMapping("/goalsJugador/{id}")
    public List<ObjetivoRendimiento>listar(@PathVariable Long id){
        return orservice.getByDeportista(id);
    }
     @GetMapping("/progresoObjetivos/{deportistaId}")
    public ResponseEntity<List<ProgresoObjetivoDTO>> getProgresoObjetivos(@PathVariable Long deportistaId) {
        List<ProgresoObjetivoDTO> resultados = orservice.obtenerProgresoObjetivos(deportistaId);
        return ResponseEntity.ok(resultados);
    }
}
