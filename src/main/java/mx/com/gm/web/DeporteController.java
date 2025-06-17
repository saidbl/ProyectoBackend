
package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Deporte;
import mx.com.gm.service.DeporteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeporteController {
    @Autowired 
    DeporteServiceImpl dservice;
    
    @GetMapping("/deportes/list")
    public List<Deporte> list(){
        return dservice.list();
    }
}
