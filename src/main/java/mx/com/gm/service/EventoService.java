package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Evento;


public interface EventoService {
    public List<Evento> listByIdDeporte(Long id);
}
