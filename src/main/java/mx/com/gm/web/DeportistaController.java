
package mx.com.gm.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import mx.com.gm.domain.Deportista;
import mx.com.gm.dto.DeportistaDTO;
import mx.com.gm.dto.DeportistaMedicionDTO;
import mx.com.gm.dto.DeportistaRendimiento;
import mx.com.gm.dto.MedicionDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.ResumenAtletasDTO;
import mx.com.gm.service.DeportistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @GetMapping("/deportista/getById/{id}")
    public ResponseEntity<Deportista> getInstructorById(@PathVariable Long id ){
        return ResponseEntity.ok(dservice.getById(id));
    }
     @PutMapping("/deportista/editar/{id}")
    public ResponseEntity<?> updateInstructor(
            @PathVariable Long id,
            @RequestPart("deportista") DeportistaDTO deportistaDTO,
            @RequestPart(value = "foto", required = false) MultipartFile file) {
         try {
        Deportista updated= dservice.update(id, deportistaDTO, file);
        return ResponseEntity.ok(updated);
                }catch (Exception e) {
                    e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al actualizar el instructor");
        }
    }
    @PostMapping(value = "/deportista/agregar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registrarDeportistaConMedicion(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("id_instructor") Long idInstructor,
            @RequestParam("id_deporte") Long idDeporte,
            @RequestParam("id_posicion") Long idPosicion,
            @RequestParam("genero") String genero,
            @RequestParam("fecha_nacimiento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam(value = "direccion", required = false) String direccion,
            @RequestParam(value = "foto_perfil", required = false) MultipartFile fotoPerfil,
            @RequestParam("medicion") String medicionJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MedicionDTO medicion = objectMapper.readValue(medicionJson, MedicionDTO.class);

            DeportistaMedicionDTO dto = new DeportistaMedicionDTO();
            dto.setNombre(nombre);
            dto.setApellido(apellido);
            dto.setEmail(email);
            dto.setIdInstructor(idInstructor);
            dto.setPassword(password);
            dto.setIdDeporte(idDeporte);
            dto.setIdPosicion(idPosicion);
            dto.setGenero(genero);
            dto.setFechaNacimiento(fechaNacimiento);
            dto.setTelefono(telefono);
            dto.setDireccion(direccion);
            dto.setFotoPerfil(fotoPerfil);
            dto.setMedicion(medicion);

            Deportista deportista = dservice.add(dto);
            System.out.println(deportista);
            return ResponseEntity.ok(deportista);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en los datos: " + e.getMessage());
        }
    }
}
