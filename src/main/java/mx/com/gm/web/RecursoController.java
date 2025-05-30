package mx.com.gm.web;


import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.RecursoRutina;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.TipoRecurso;
import mx.com.gm.service.RecursoRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {
    @Autowired
    private RecursoRutinaService recursoService;

    @PostMapping("/ejercicio/{ejercicioId}")
    public ResponseEntity<RecursoRutina> subirRecurso(
            @PathVariable Long ejercicioId,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(required = false) String descripcion) {
        
        try {
            RecursoRutina recurso = recursoService.subirArchivo(ejercicioId, archivo, descripcion);
            return ResponseEntity.ok(recurso);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/ejercicio/{ejercicioId}")
    public ResponseEntity<List<RecursoRutina>> obtenerRecursosEjercicio(
            @PathVariable Long ejercicioId,
            @RequestParam(required = false) TipoRecurso tipo) {
        
        List<RecursoRutina> recursos = (tipo != null) 
            ? recursoService.obtenerRecursosPorTipo(ejercicioId, tipo)
            : recursoService.obtenerRecursosPorEjercicio(ejercicioId);
        
        return ResponseEntity.ok(recursos);
    }
    
    @GetMapping("/ejercicio/videos/{instructorId}")
    public ResponseEntity<List<RecursoRutina>> listarVideos(@PathVariable Long instructorId) {
        List<RecursoRutina> recursos = recursoService.listByInstructor(instructorId);
        return ResponseEntity.ok(recursos);
    }
    @DeleteMapping("/ejercicio/eliminar/{id}")
    public ResponseEntity<ResponseAPI> eliminarVideos(@PathVariable Long id) throws IOException{
        recursoService.eliminarRecurso(id);
        ResponseAPI response = new ResponseAPI();
        response.setMessage("Eliminado correctamente");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
}

