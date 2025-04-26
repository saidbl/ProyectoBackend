package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.OrganizacionDTO;
import mx.com.gm.dto.ResponseAPI;

public interface OrganizacionService {
    public List<Organizacion> list();
    public ResponseAPI login(ResponseAPI login);
    public Organizacion getbyId(Long id);
    public Organizacion update(Long id, OrganizacionDTO odto);
}
