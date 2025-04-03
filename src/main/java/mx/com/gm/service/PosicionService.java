package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Posicion;

public interface PosicionService {
    public List<Posicion> listByDepId (Long id);
}
