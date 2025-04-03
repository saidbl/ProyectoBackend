package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Posicion;
import mx.com.gm.service.PosicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PosicionController {
    @Autowired
    PosicionService pservice;
    
    @GetMapping("/posiciones")
    public List<Posicion> list(@RequestParam Long id){
        System.out.println(id);
        return pservice.listByDepId(id);
    }
}
