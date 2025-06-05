package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import mx.com.gm.dao.DeporteDao;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.TipoMetricaDao;
import mx.com.gm.domain.Deporte;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.TipoMetrica;
import mx.com.gm.dto.TipoMetricaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoMetricaServiceImpl implements TipoMetricaService{
    @Autowired 
    TipoMetricaDao tmdao;
    
    @Autowired 
    DeporteDao ddao;
    
    @Autowired
    DeportistaDao dddao;

    @Override
    public TipoMetrica add(TipoMetricaDTO tmdto) {
        TipoMetrica metrica = new TipoMetrica();
        Deporte d = ddao.findById(tmdto.getIddeporte())
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        Deportista dd=dddao.findById(tmdto.getIddeportista())
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        metrica.setDeporte(d);
        metrica.setDeportista(dd);
        metrica.setEsObjetivo(tmdto.isEsObjetivo());
        metrica.setNombre(tmdto.getNombre());
        metrica.setUnidad(tmdto.getUnidad());
        return tmdao.save(metrica);
    }

    @Override
    public List<TipoMetrica> list(Long id) {
        return tmdao.findByDeportistaId(id);
    }

    @Override
    public void delete(Long id) throws IOException {
       tmdao.deleteById(id);
    }
    
}
