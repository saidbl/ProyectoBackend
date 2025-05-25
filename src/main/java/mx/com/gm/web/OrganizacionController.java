
package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.OrganizacionDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.OrganizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizacionController {
    @Autowired
    OrganizacionService oservice;
    @GetMapping("/listarOrganizacion")
    public List<Organizacion> list(){
        return oservice.list();
    }
    @GetMapping("/obtenerOrg/{id}")
    public Organizacion getbyId(@PathVariable Long id) {
        return oservice.getbyId(id);
    }
     @PostMapping("/auth/login/organizacion")
    public ResponseEntity<ResponseAPI> login (@RequestBody ResponseAPI req){
        return ResponseEntity.ok(oservice.login(req));
    }
     @PutMapping("/actualizarOrg/{id}")
    public ResponseEntity<Organizacion> actualizarOrg(@PathVariable Long id, @RequestBody OrganizacionDTO oDTO) {
        Organizacion orgActualizado = oservice.update(id, oDTO);
        return ResponseEntity.ok(orgActualizado);
    }
    @GetMapping("/listarOrganizacion/{id}")
    public List<Organizacion> listByDeporte(@PathVariable Long id){
        return oservice.listByDeporte(id);
    }
}
