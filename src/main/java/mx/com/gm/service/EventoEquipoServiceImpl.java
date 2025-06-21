package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.EquipoDao;
import mx.com.gm.dao.EventoDao;
import mx.com.gm.dao.EventoEquipoDao;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.Evento;
import mx.com.gm.domain.EventoEquipo;
import mx.com.gm.dto.EventoEquipoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoEquipoServiceImpl implements EventoEquipoService{

    @Autowired
    EventoEquipoDao eedao;
    
    @Autowired
    EventoDao ddao;
    
    @Autowired
    EquipoDao edao;
    
    @Override
    public List<EventoEquipo> listByIdEvento(Long id) {
         return eedao.findByEventoId(id);
    }

    @Override
    public EventoEquipo add(EventoEquipoDTO ee) {
        Evento d = ddao.findById(ee.getIdEvento())
                .orElseThrow(() -> new RuntimeException("Deportista no encontrado"));

        Equipo e = edao.findById(ee.getIdEquipo())
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        
    Long equiposAsociados = d.getEquiposInscritos();
    equiposAsociados ++;
    d.setEquiposInscritos(equiposAsociados);
    ddao.save(d);
    EventoEquipo eventoEquipo = new EventoEquipo();
    eventoEquipo.setEvento(d);
    eventoEquipo.setEquipo(e);

    return eedao.save(eventoEquipo);
    }

    @Override
    public List<Equipo> listarEquiposEvento(Long idEvento) {
        return eedao.findEquiposByEventoId(idEvento);
    }

    @Override
    public List<Evento> listarEventosEquipo(Long idEquipo) {
        return eedao.findEventosByEquipoId(idEquipo);
    }

    @Override
    public void desasociarEquipo(Long idevento, Long idequipo) {
        Evento e = ddao.findById(idevento)
                .orElseThrow(() -> new RuntimeException("Deportista no encontrado"));
        Long n = e.getEquiposInscritos();
        n--;
        e.setEquiposInscritos(n);
        ddao.save(e);
        eedao.deleteByEquipoIdAndEventoId(idequipo, idevento);
    }
    
}
