
package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Evento;
import mx.com.gm.dto.EventoDTO;
import mx.com.gm.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventoController {
    
    @Autowired
    EventoService eservice;
    
    @GetMapping("/eventosEquipo")
    public List<Evento> list(@RequestParam Long id){
        System.out.println(id);
        return eservice.listByIdDeporte(id);
    }
    @GetMapping("/eventosFuturosDep/{id}")
    public ResponseEntity<List<Evento>> listEventosByDeportista(@PathVariable Long id){
        return ResponseEntity.ok( eservice.ProximosEventosByDeportistaId(id));
    }
    
    @PostMapping("/organizacion/crear")
    public ResponseEntity<Evento> crearEvento(@RequestBody EventoDTO eventoDTO) {
        System.out.println(eventoDTO);
        Evento evento = eservice.crearEventoconFechas(eventoDTO);
        return ResponseEntity.ok(evento);
    }
}
