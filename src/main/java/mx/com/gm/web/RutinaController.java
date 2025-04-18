package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Rutina;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.RutinaDTO;
import mx.com.gm.service.RutinaService;
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
public class RutinaController {
    @Autowired 
    private RutinaService rservice;
    
    @GetMapping("/rutinas")
    public List<Rutina> list(@RequestParam Long id){
        System.out.println(id);
        return rservice.listbyInstId(id);
    }
    @DeleteMapping("/rutinas/eliminar/{id}")
    public ResponseEntity<ResponseAPI> eliminarRutina(@PathVariable Long id) {
        rservice.delete(id);
        ResponseAPI response = new ResponseAPI();
        response.setMessage("Eliminado correctamente");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/rutinas/agregar")
    public ResponseEntity<Rutina> agregarRutina(@RequestBody RutinaDTO rutina) {
        System.out.println(rutina.getObjetivo());
        Rutina nuevaRutina = rservice.add(rutina);
        return ResponseEntity.ok(nuevaRutina);
    }
    @GetMapping("/rutinas/total/{instructorId}")
    public ResponseEntity<Long> getTotalRutinasByInstructorId(@PathVariable Long instructorId) {
        Long totalRutinas = rservice.getTotalRutinasByInstructorId(instructorId);
        return ResponseEntity.ok(totalRutinas);
    }
    @GetMapping("/rutinas/{instructorId}/top3")
    public ResponseEntity<List<Rutina>> getTop3RutinasByInstructor(
            @PathVariable Long instructorId) {
        
        List<Rutina> rutinas = rservice.getTop3RutinasPopularesPorInstructor(instructorId);
        return ResponseEntity.ok(rutinas);
    }
     @GetMapping("/rutinasdeportista/{deportistaId}")
    public ResponseEntity<List<RutinaDTO>> getRutinasPorDeportista(
            @PathVariable Long deportistaId) {
        List<RutinaDTO> rutinas = rservice.rutinasEjerciciosRecursos(deportistaId);
        return ResponseEntity.ok(rutinas);
    }
}
