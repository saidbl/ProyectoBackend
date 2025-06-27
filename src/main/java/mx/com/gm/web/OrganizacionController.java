
package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.InstructorDTO;
import mx.com.gm.dto.OrganizacionDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.OrganizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OrganizacionController {
    @Autowired
    OrganizacionService oservice;
    
     @PostMapping("/org/agregar")
    public ResponseEntity<?> createInstructor(
            @RequestPart("organizacion") OrganizacionDTO oDTO,
            @RequestPart(value = "foto", required = false) MultipartFile file) {
         try {
        Organizacion added= oservice.add(oDTO, file);
        return ResponseEntity.ok(added);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en los datos: " + e.getMessage());
        }
    }
    @GetMapping("/listarOrganizacion")
    public List<Organizacion> list(){
        return oservice.list();
    }
        @DeleteMapping("/organizacion/eliminar/{id}")
    public ResponseEntity<ResponseAPI> eliminarOrganizacion(@PathVariable Long id) {
        try{
        oservice.delete(id);
        ResponseAPI response = new ResponseAPI();
        response.setMessage("Eliminado correctamente");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
         } catch (Exception e) {
        ResponseAPI errorResponse = new ResponseAPI();
        errorResponse.setMessage("Error al eliminar el organizacion");
        errorResponse.setSuccess(false);
        return ResponseEntity.internalServerError().body(errorResponse);
        }
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
    public ResponseEntity<?> updateOrganizacion(
            @PathVariable Long id,
            @RequestPart("organizacion") OrganizacionDTO organizacionDTO,
            @RequestPart(value = "foto", required = false) MultipartFile file) {
         try {
        Organizacion updated= oservice.update(id, organizacionDTO, file);
        return ResponseEntity.ok(updated);
                }catch (Exception e) {
                    e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al actualizar el organizacion");
        }
    }
    @GetMapping("/listarOrganizacion/{id}")
    public List<Organizacion> listByDeporte(@PathVariable Long id){
        return oservice.listByDeporte(id);
    }
}
