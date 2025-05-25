package mx.com.gm.web;

import java.util.List;
import java.util.Map;
import mx.com.gm.domain.CheckinRutina;
import mx.com.gm.dto.CheckinRutinaDTO;
import mx.com.gm.dto.CumplimientoRutinasDTO;
import mx.com.gm.dto.ResumenCumplimientoDTO;
import mx.com.gm.service.CheckinRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckinRutinaController {
    @Autowired 
    CheckinRutinaService crservice;
    
    @PostMapping("/checkin/agregar")
    public ResponseEntity<CheckinRutina> add (@RequestBody CheckinRutinaDTO crdto){
        System.out.println(crdto);
        CheckinRutina nuevoCheckin = crservice.add(crdto);
        
        return ResponseEntity.ok(nuevoCheckin);
    }
    
    @GetMapping("/checkin/completadas/{id}")
    public List<CheckinRutina> list (@PathVariable Long id){
        System.out.println(id);
        return crservice.listByDeportistaId(id);
    }
    @GetMapping("/checkin/incompletadas/{id}")
    public List<CheckinRutina> listIncompletas (@PathVariable Long id){
        return crservice.listByDeportistaIdIncompleta(id);
    }
    
     @GetMapping("/cumplimiento/{deportistaId}")
    public ResponseEntity<?> getCumplimientoRutinas(
            @PathVariable Long deportistaId,
            @RequestParam(defaultValue = "1m") String rango) {

        try {
            CumplimientoRutinasDTO dto = crservice.getCumplimiento(deportistaId, rango);
            return ResponseEntity.ok(dto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/estadisticasInstructor/{id}")
        public ResponseEntity<ResumenCumplimientoDTO> getCumplimientoRutinas(@PathVariable Long id) {
            return ResponseEntity.ok(crservice.obtenerCumplimientoRutinas(id));
        }
}
