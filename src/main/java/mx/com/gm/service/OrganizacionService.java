package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.OrganizacionDTO;
import mx.com.gm.dto.ResponseAPI;
import org.springframework.web.multipart.MultipartFile;

public interface OrganizacionService {
    public List<Organizacion> list();
    public ResponseAPI login(ResponseAPI login);
    public Organizacion getbyId(Long id);
    public Organizacion update(Long id, OrganizacionDTO odto, MultipartFile file)throws IOException;
    public List<Organizacion> listByDeporte (Long id);
     public Organizacion add(OrganizacionDTO idto, MultipartFile file)throws IOException;
     public void delete(Long id)throws IOException;
}
