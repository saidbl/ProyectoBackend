package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.EquipoDao;
import mx.com.gm.dao.JugadorEquipoDao;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.JugadorEquipo;
import mx.com.gm.dto.JugadorEquipoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JugadorEquipoServiceImpl implements JugadorEquipoService{
    
    @Autowired
    JugadorEquipoDao jedao;
    
    @Autowired
    DeportistaDao ddao;
    
    @Autowired
    EquipoDao edao;

    @Override
    public List<JugadorEquipo> listByIdEquipo(Long id) {
        return jedao.findByEquipoId(id);
    }

    @Override
    public JugadorEquipo add(JugadorEquipoDTO rj) {
        Deportista d = ddao.findById(rj.getIdJugador())
                .orElseThrow(() -> new RuntimeException("Deportista no encontrado"));

        Equipo e = edao.findById(rj.getIdEquipo())
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        

    Integer jugadoresAsociados = e.getJugadoresAsociados();
    jugadoresAsociados ++;
    e.setJugadoresAsociados(jugadoresAsociados);
    edao.save(e);
    JugadorEquipo jugadorEquipo = new JugadorEquipo();
    jugadorEquipo.setDeportista(d);
    jugadorEquipo.setEquipo(e);

    return jedao.save(jugadorEquipo);
    }
    
}
