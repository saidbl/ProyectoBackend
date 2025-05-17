package mx.com.gm.web;

import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.Equipo;
import mx.com.gm.dto.EquipoDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        try{
        eservice.delete(id);
        ResponseAPI response = new ResponseAPI();
        response.setMessage("Eliminado correctamente");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
         } catch (Exception e) {
        ResponseAPI errorResponse = new ResponseAPI();
        errorResponse.setMessage("Error al eliminar el equipo");
        errorResponse.setSuccess(false);
        return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    @PostMapping(value = "/equipos/agregar",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Equipo> agregarEquipo( @RequestPart("archivo") MultipartFile archivo,
    @RequestPart("equipo") EquipoDTO equipoDto) {
        System.out.println(equipoDto);
        try{
        Equipo e = eservice.add(equipoDto,archivo);
        return ResponseEntity.ok(e);
        }catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/equiposDeportista/{id}")
    public List<Equipo> listByDeportistaId (@PathVariable Long id){
        return eservice.listbyidjugador(id);
    }
    
    @PutMapping(value = "/equipos/editar/{id}",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateEquipo(
            @PathVariable Long id,
            @RequestPart("equipo") EquipoDTO equipoDTO,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        if (!id.equals(equipoDTO.getId())) {
            return ResponseEntity.badRequest().body("ID no coincide");
        }
        try {
            Equipo equipoActualizado = eservice.updateEquipo(equipoDTO, imagen);
            System.out.println(imagen);
            return ResponseEntity.ok(equipoActualizado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el equipo");
        }
    }
}
