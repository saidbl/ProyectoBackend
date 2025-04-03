package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Equipo;
import mx.com.gm.dto.EquipoDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.EquipoService;
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
public class EquipoController {
    @Autowired
    EquipoService eservice;
    
    @GetMapping("/equipos")
    public List<Equipo> list(@RequestParam Long id){
        System.out.println(id);
        return eservice.listByIdInstructor(id);
    }
    @DeleteMapping("/equipos/eliminar/{id}")
    public ResponseEntity<ResponseAPI> eliminarEquipo(@PathVariable Long id) {
        eservice.delete(id);
        ResponseAPI response = new ResponseAPI();
        response.setMessage("Eliminado correctamente");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/equipos/agregar")
    public ResponseEntity<Equipo> agregarEquipo(@RequestBody EquipoDTO equipo) {
        Equipo nuevoEquipo = eservice.add(equipo);
        return ResponseEntity.ok(nuevoEquipo);
    }
}
