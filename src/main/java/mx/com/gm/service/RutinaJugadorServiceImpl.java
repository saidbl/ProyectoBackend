package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.RutinaDao;
import mx.com.gm.dao.RutinaJugadorDao;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Rutina;
import mx.com.gm.domain.RutinaJugador;
import mx.com.gm.dto.RutinaJugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutinaJugadorServiceImpl implements RutinaJugadorService{
    
    @Autowired
    RutinaJugadorDao rdao;
    
    @Autowired
    DeportistaDao ddao;
    
    @Autowired
    RutinaDao rrdao;

    @Override
    public List<RutinaJugador> listbyJugadorId(Long id) {
         return rdao.findByDeportistaId(id);
    }

    @Override
    public RutinaJugador add(RutinaJugadorDTO rj) {
        Deportista d = ddao.findById(rj.getIdJugador())
                .orElseThrow(() -> new RuntimeException("Deportista no encontrado"));

        Rutina r = rrdao.findById(rj.getIdRutina())
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        RutinaJugador rjj= new RutinaJugador();
        rjj.setDeportista(d);
        rjj.setRutina(r);

        return rdao.save(rjj);
    }
    
}
