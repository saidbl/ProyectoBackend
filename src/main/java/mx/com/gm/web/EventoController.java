
package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Evento;
import mx.com.gm.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    
}
