package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.EventoFecha;
import mx.com.gm.service.EventoFechaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventoFechaController {
    @Autowired 
    EventoFechaService efservice;
    
    @GetMapping("/eventoFecha/listar/{id}")
    public List<EventoFecha> listarFechasPorEvento(@PathVariable Long id){
        return efservice.findbyIdEvento(id);
    }
    
}
