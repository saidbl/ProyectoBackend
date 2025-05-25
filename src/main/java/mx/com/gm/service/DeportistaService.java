package mx.com.gm.service;
import java.util.List;
import mx.com.gm.domain.Deportista;
import mx.com.gm.dto.DeportistaRendimiento;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.ResumenAtletasDTO;
public interface DeportistaService {
    public List<Deportista> list();
    public ResponseAPI login(ResponseAPI login);
    public List<Deportista> listByIdInstructor(Long id);
    public List<DeportistaRendimiento> listByIdInstructorObjRendCheck(Long id);
    public ResumenAtletasDTO obtenerResumenAtletas(Long instructorId) ;
    
}
