package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.EventoEquipo;
import mx.com.gm.dto.EventoEquipoDTO;


public interface EventoEquipoService {
    public List<EventoEquipo> listByIdEvento(Long id);
    public EventoEquipo add(EventoEquipoDTO ee);
}
