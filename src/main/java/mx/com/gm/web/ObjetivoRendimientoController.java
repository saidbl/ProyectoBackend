package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.ObjetivoRendimiento;
import mx.com.gm.service.ObjetivoRendimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ObjetivoRendimientoController {
    @Autowired
    ObjetivoRendimientoService orservice;
    @GetMapping("/deportistaRendimiento/{idDeportista}")
    public List<ObjetivoRendimiento> getByDeportista(@PathVariable Long id){
        return orservice.getByDeportista(id);
    }
}
