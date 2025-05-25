
package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Deportista;
import mx.com.gm.dto.DeportistaRendimiento;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.ResumenAtletasDTO;
import mx.com.gm.service.DeportistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeportistaController {
    @Autowired
    DeportistaService dservice;
    
     @GetMapping("/deportista")
    public List<Deportista> list(){
        return dservice.list();
    }
     @PostMapping("/auth/login/deportista")
    public ResponseEntity<ResponseAPI> login (@RequestBody ResponseAPI req){
        return ResponseEntity.ok(dservice.login(req));
    }
    @GetMapping("/deportistas")
    public List<Deportista> list(@RequestParam Long id){
        System.out.println(id);
        return dservice.listByIdInstructor(id);
    }
    @GetMapping("/deportistas/CheckRendObj")
    public List<DeportistaRendimiento> listCheckRenObk(@RequestParam Long id){
        return dservice.listByIdInstructorObjRendCheck(id);
    }
    @GetMapping("/deportistas/resumen/{instructorId}")
    public ResponseEntity<ResumenAtletasDTO> getResumenAtletas(
        @PathVariable Long instructorId
    ) {
        return ResponseEntity.ok(dservice.obtenerResumenAtletas(instructorId));
    }
}
