package mx.com.gm.web;

import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.Instructor;
import mx.com.gm.dto.InstructorDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/instructor/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long id ){
        return ResponseEntity.ok(iservice.getById(id));
    }
     @PutMapping("/instructor/editar/{id}")
    public ResponseEntity<?> updateInstructor(
            @PathVariable Long id,
            @RequestPart("instructor") InstructorDTO instructorDTO,
            @RequestPart(value = "foto", required = false) MultipartFile file) {
         try {
        Instructor updated= iservice.update(id, instructorDTO, file);
        return ResponseEntity.ok(updated);
                }catch (Exception e) {
                    e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al actualizar el instructor");
        }
    }
    @GetMapping("/instructores")
    public ResponseEntity<Instructor> list(@RequestParam Long id){
        System.out.println(id);
        return ResponseEntity.ok(iservice.listByIdDeportista(id));
    }
    @GetMapping("/instructorOrg/{id}")
    public List<Instructor> getByDeporte (@PathVariable Long id){
        return iservice.listByDeporte(id);
    }
    
     @PostMapping("/instructorOrg/agregar")
    public ResponseEntity<Instructor> createInstructor(
            @RequestPart("instructor") InstructorDTO instructorDTO,
            @RequestPart(value = "foto", required = false) MultipartFile file) {
         try {
        Instructor added= iservice.add(instructorDTO, file);
        return ResponseEntity.ok(added);
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
