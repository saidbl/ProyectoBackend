package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.ResponseAPI;

public interface OrganizacionService {
    public List<Organizacion> list();
    public ResponseAPI login(ResponseAPI login);
}
