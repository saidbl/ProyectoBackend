package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Evento;
import mx.com.gm.dto.EventoDTO;


public interface EventoService {
    public List<Evento> listByIdDeporte(Long id);
    public List<Evento> ProximosEventosByDeportistaId(Long deportista);
    public Evento crearEventoconFechas(EventoDTO edto);
}
