package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.Evento;
import mx.com.gm.domain.EventoEquipo;
import mx.com.gm.dto.EventoEquipoDTO;


public interface EventoEquipoService {
    public List<EventoEquipo> listByIdEvento(Long id);
    public EventoEquipo add(EventoEquipoDTO ee);
    public List<Equipo> listarEquiposEvento(Long idEvento);
    public List<Evento> listarEventosEquipo(Long idEquipo);
    public void desasociarEquipo (Long idevento,Long idequipo );
}
