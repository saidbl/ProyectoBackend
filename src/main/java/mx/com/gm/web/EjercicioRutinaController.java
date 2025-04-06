package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.EjercicioRutina;
import mx.com.gm.service.EjercicioRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EjercicioRutinaController {
    @Autowired
    EjercicioRutinaService eservice;
    
    @GetMapping("/ejercicios")
    public List<EjercicioRutina> list(@RequestParam  Long id){
        System.out.println(eservice.listarPorIdInstructor(id));
        return eservice.listarPorIdInstructor(id);
    }
}
