
package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.EventoDao;
import mx.com.gm.domain.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoServiceImpl implements EventoService{
    
    @Autowired
    EventoDao edao;

    @Override
    public List<Evento> listByIdDeporte(Long id) {
        return edao.findByDeporteId(id);
    }

    @Override
    public List<Evento> ProximosEventosByDeportistaId(Long deportista) {
        return edao.findProximosEventosByDeportistaId(deportista);
    }
    
}
