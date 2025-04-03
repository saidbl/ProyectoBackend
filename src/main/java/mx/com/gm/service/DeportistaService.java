
package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Deportista;
import mx.com.gm.dto.ResponseAPI;


public interface DeportistaService {
    public List<Deportista> list();
    public ResponseAPI login(ResponseAPI login);
    public List<Deportista> listByIdInstructor(Long id);
    
}
