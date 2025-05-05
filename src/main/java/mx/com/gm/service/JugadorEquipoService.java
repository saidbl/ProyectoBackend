package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.JugadorEquipo;
import mx.com.gm.dto.JugadorEquipoDTO;

public interface JugadorEquipoService {
    public List<JugadorEquipo> listByIdEquipo(Long id);
    public JugadorEquipo add(JugadorEquipoDTO rj);
    public void delete(Long id);
}
