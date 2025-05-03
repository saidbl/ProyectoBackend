package mx.com.gm.web;

import java.util.List;
import java.util.Map;
import mx.com.gm.dto.EvolucionFisicaDTO;
import mx.com.gm.service.MedicionFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicionFisicaController {
    @Autowired
    MedicionFisicaService mfservice;
    
    @GetMapping("/evolucion/{deportistaId}")
    public ResponseEntity<?> getEvolucionFisica(
            @PathVariable Long deportistaId,
            @RequestParam(defaultValue = "todo") String rango) {

        try {
            List<EvolucionFisicaDTO> evolucion = mfservice.getEvolucionFisica(deportistaId, rango);
            
            if (evolucion.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "No hay mediciones registradas en el rango seleccionado"));
            }

            return ResponseEntity.ok(evolucion);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
