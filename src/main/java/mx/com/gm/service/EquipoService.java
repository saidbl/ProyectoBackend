package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.Equipo;
import mx.com.gm.dto.EquipoDTO;
import org.springframework.web.multipart.MultipartFile;

public interface EquipoService {
    public List<Equipo> listByIdInstructor(Long id);
    void delete(Long id);
    public Equipo add(EquipoDTO e,MultipartFile archivo)throws IOException;
    public List<Equipo> listbyidjugador(Long id);
    public Equipo updateEquipo(EquipoDTO equipoDTO, MultipartFile imagen) throws IOException;
}
