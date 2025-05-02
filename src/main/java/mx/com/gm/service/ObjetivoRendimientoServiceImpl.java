package mx.com.gm.service;

import java.time.LocalDate;
import java.util.List;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.ObjetivoRendimientoDao;
import mx.com.gm.dao.TipoMetricaDao;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.ObjetivoRendimiento;
import mx.com.gm.domain.TipoMetrica;
import mx.com.gm.dto.ObjetivoRendimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjetivoRendimientoServiceImpl implements ObjetivoRendimientoService{
    @Autowired
    ObjetivoRendimientoDao ordao;
    
    @Autowired
    DeportistaDao ddao;
    
    @Autowired
    TipoMetricaDao tmdao;

    @Override
    public List<ObjetivoRendimiento> getByDeportista(Long id) {
        return ordao.findByDeportistaId(id);
    }

    @Override
    public ObjetivoRendimiento add(ObjetivoRendimientoDTO ordto) {
        ObjetivoRendimiento or = new ObjetivoRendimiento();
        Deportista d = ddao.findById(ordto.getIddeportista())
                 .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        LocalDate f = LocalDate.now();
        TipoMetrica m = tmdao.findById(ordto.getIdmetrica())
                  .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        or.setCompletado(false);
        or.setDeportista(d);
        or.setFechaEstablecido(f);
        or.setFechaObjetivo(ordto.getFechaObjetivo());
        or.setMetrica(m);
        or.setPrioridad(ordto.getPrioridad());
        or.setValorObjetivo(ordto.getValorObjetivo());
        return ordao.save(or);
    }
    
}
