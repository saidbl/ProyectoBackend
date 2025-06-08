package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.Evento;
import mx.com.gm.domain.EventoEquipo;
import mx.com.gm.dto.EventoEquipoDTO;
import mx.com.gm.service.EventoEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventoEquipoController {
    @Autowired
    EventoEquipoService eeservice;
    
    @GetMapping("/eventosEquipo/equipos")
    public List<EventoEquipo> list(@RequestParam Long id){
        return eeservice.listByIdEvento(id);
    }
    @PostMapping("/eventosEquipo/vincular")
    public ResponseEntity<EventoEquipo> agregarJugador(@RequestBody EventoEquipoDTO vinculacion) {
        System.out.println(vinculacion);
        EventoEquipo nuevaVinculacion = eeservice.add(vinculacion);
        return ResponseEntity.ok(nuevaVinculacion);
    }
    @GetMapping("/eventosEquipo/listar/{id}")
    public List<Equipo> listarEquiposEvento(@PathVariable Long id){
        return eeservice.listarEquiposEvento(id);
    }
    @GetMapping("/equiposEvento/listar/{id}")
    public List<Evento> listarEventosEquipo(@PathVariable Long id){
        return eeservice.listarEventosEquipo(id);
    }
}
