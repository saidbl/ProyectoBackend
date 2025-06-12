package mx.com.gm.service;
import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.Deportista;
import mx.com.gm.dto.DeportistaDTO;
import mx.com.gm.dto.DeportistaMedicionDTO;
import mx.com.gm.dto.DeportistaRendimiento;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.ResumenAtletasDTO;
import org.springframework.web.multipart.MultipartFile;
public interface DeportistaService {
    public List<Deportista> list();
    public ResponseAPI login(ResponseAPI login);
    public List<Deportista> listByIdInstructor(Long id);
    public List<DeportistaRendimiento> listByIdInstructorObjRendCheck(Long id);
    public ResumenAtletasDTO obtenerResumenAtletas(Long instructorId) ;
    public Deportista getById(Long id);
    public Deportista update(Long id, DeportistaDTO idto, MultipartFile file)throws IOException;
     public Deportista add(DeportistaMedicionDTO dto)throws IOException;
    
    
}
