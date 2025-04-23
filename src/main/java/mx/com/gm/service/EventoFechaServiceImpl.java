package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.EventoFechaDao;
import mx.com.gm.domain.EventoFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoFechaServiceImpl implements EventoFechaService{
    @Autowired 
    EventoFechaDao efdao;

    @Override
    public List<EventoFecha> findbyIdEvento(Long idEvento) {
        return efdao.findByEventoId(idEvento);
    }
    
}
