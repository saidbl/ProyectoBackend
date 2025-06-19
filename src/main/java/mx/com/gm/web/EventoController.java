package mx.com.gm.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import mx.com.gm.domain.Evento;
import mx.com.gm.dto.EventoConEquiposDTO;
import mx.com.gm.dto.EventoDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    
    @GetMapping("/eventosFuturosOrg/{id}")
    public ResponseEntity<List<Evento>> listEventosByOrganizacion(@PathVariable Long id){
        return ResponseEntity.ok( eservice.ProximosEventosByOrganizacionId(id));
    }
    
    
    @PostMapping(value = "/organizacion/crear",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Evento> crearEvento( @RequestPart("archivo") MultipartFile archivo,
    @RequestPart("evento") EventoDTO eventoDto) {
        System.out.println(eventoDto);
        try{
        Evento evento = eservice.crearEventoconFechas(eventoDto,archivo);
        return ResponseEntity.ok(evento);
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
     @PutMapping("/actualizarEvento/{id}")
    public ResponseEntity<?> updateEvento(
            @PathVariable Long id,
            @RequestPart("evento") EventoDTO eventoDTO,
            @RequestPart(value = "foto", required = false) MultipartFile file) {
         try {
        Evento updated= eservice.actualizarEvento(id, eventoDTO, file);
             System.out.println(eventoDTO);
        return ResponseEntity.ok(updated);
                }catch (Exception e) {
                    e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al actualizar el organizacion");
        }
    }
    @DeleteMapping("/eliminarEvento/{id}")
    public ResponseEntity<ResponseAPI> deleteEvento(@PathVariable Long id){
        eservice.eliminarEvento(id);
        ResponseAPI response = new ResponseAPI();
        response.setMessage("Eliminado correctamente");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
     @GetMapping("/proximosEquipos/{id}")
    public List<EventoConEquiposDTO> getProximosEventosConEquipos(@PathVariable Long id) {
        return eservice.getProximosEventosConEquipos(id);
    }
    @GetMapping("/generalesorg/{id}")
    public ResponseEntity<Map<String, Object>> getEstadisticasGenerales(@PathVariable Long id) {
        return ResponseEntity.ok(eservice.getEstadisticasGenerales(id));
    }
}
