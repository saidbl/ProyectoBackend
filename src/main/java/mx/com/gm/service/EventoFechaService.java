package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.EventoFecha;

public interface EventoFechaService {
    public List<EventoFecha> findbyIdEvento(Long idEvento);
}
