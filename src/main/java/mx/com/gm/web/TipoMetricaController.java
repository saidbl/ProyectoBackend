package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.TipoMetrica;
import mx.com.gm.dto.TipoMetricaDTO;
import mx.com.gm.service.TipoMetricaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TipoMetricaController {
    @Autowired 
    TipoMetricaService tmservice;
    
    @GetMapping("/metricasJugador/{id}")
    public List<TipoMetrica> list (@PathVariable Long id){
        return tmservice.list(id);
    }
    
    @PostMapping("/agregarMetrica")
    public ResponseEntity<TipoMetrica>add(@RequestBody TipoMetricaDTO tmdto){
        return ResponseEntity.ok(tmservice.add(tmdto));
    }
}
