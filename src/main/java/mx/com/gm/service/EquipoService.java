package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Equipo;
import mx.com.gm.dto.EquipoDTO;

public interface EquipoService {
    public List<Equipo> listByIdInstructor(Long id);
    void delete(Long id);
    public Equipo add(EquipoDTO e);
    public List<Equipo> listbyidjugador(Long id);
}
