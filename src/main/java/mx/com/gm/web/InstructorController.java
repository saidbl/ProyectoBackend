package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Instructor;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstructorController {
    @Autowired
    InstructorService iservice;
     @GetMapping("/listarInstructor")
    public List<Instructor> list(){
        return iservice.list();
    }
     @PostMapping("/auth/login/instructor")
    public ResponseEntity<ResponseAPI> login (@RequestBody ResponseAPI req){
        return ResponseEntity.ok(iservice.login(req));
    }
    
}
