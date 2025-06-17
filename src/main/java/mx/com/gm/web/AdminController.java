
package mx.com.gm.web;

import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    
    @Autowired
    AdminService aservice;
    
     @PostMapping("/auth/login/admin")
    public ResponseEntity<ResponseAPI> login (@RequestBody ResponseAPI req){
        return ResponseEntity.ok(aservice.login(req));
    }
}
