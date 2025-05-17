package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.EjercicioRutina;
import mx.com.gm.dto.EjercicioRutinaDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.EjercicioRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EjercicioRutinaController {
    @Autowired
    EjercicioRutinaService eservice;
    
    @GetMapping("/ejercicios")
    public List<EjercicioRutina> list(@RequestParam  Long id){
        System.out.println(eservice.listarPorIdInstructor(id));
        return eservice.listarPorIdInstructor(id);
    }
    
    @PostMapping("/ejercicios/agregar")
    public ResponseEntity<EjercicioRutina> add (@RequestBody EjercicioRutinaDTO edto){
        EjercicioRutina nuevoejercicio = eservice.add(edto);
        return ResponseEntity.ok(nuevoejercicio);
    }
    
    @DeleteMapping("/ejercicios/eliminar/{id}")
    public ResponseEntity<ResponseAPI> delete(@PathVariable Long id) {
        eservice.delete(id);
        ResponseAPI response = new ResponseAPI();
        response.setMessage("Eliminado correctamente");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/ejercicios/editar/{id}")
    public ResponseEntity<EjercicioRutina> edit(@PathVariable Long id, @RequestBody EjercicioRutinaDTO edto) {
        EjercicioRutina er = eservice.edit(id,edto);
        return ResponseEntity.ok(er);
    }
}
