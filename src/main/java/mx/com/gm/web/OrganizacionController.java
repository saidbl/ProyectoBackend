
package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.OrganizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     @PostMapping("/auth/login/organizacion")
    public ResponseEntity<ResponseAPI> login (@RequestBody ResponseAPI req){
        return ResponseEntity.ok(oservice.login(req));
    }
}
