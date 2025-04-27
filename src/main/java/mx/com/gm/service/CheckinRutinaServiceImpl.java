package mx.com.gm.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import mx.com.gm.dao.CheckinRutinaDao;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.RutinaDao;
import mx.com.gm.domain.CheckinRutina;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Rutina;
import mx.com.gm.dto.CheckinRutinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckinRutinaServiceImpl implements CheckinRutinaService{
    @Autowired 
    CheckinRutinaDao crdao;
    
    @Autowired 
    RutinaDao rdao ;
    
    @Autowired 
    DeportistaDao ddao ;

    @Override
    public CheckinRutina add(CheckinRutinaDTO crdto) {
        CheckinRutina cr = new CheckinRutina();
        System.out.println(crdto);
        Rutina r = rdao.findById(crdto.getIdrutina())
                .orElseThrow(()->new RuntimeException("Rutina no encontrada"));
        Deportista d = ddao.findById(crdto.getIdjugador())
                .orElseThrow(()->new RuntimeException("Deportista no encontrado"));
        LocalTime hora = LocalTime.now();
        LocalDate fecha = LocalDate.now();
        cr.setRutina(r);
        cr.setJugador(d);
        cr.setHora(hora);
        cr.setFecha(fecha);
        cr.setEstado(CheckinRutina.EstadoCheckin.COMPLETADA);
        cr.setComentarios(crdto.getComentarios());
        return crdao.save(cr);
    }

    @Override
    public List<CheckinRutina> listByDeportistaId(Long id) {
        return crdao.findByDeportistaIdAndEstado(id);
    }
    }

