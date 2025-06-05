package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.RegistroRendimientoDao;
import mx.com.gm.dao.TipoMetricaDao;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.RegistroRendimiento;
import mx.com.gm.domain.TipoMetrica;
import mx.com.gm.dto.RegistroRendimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroRendimientoServiceImpl implements RegistroRendimientoService {
    @Autowired 
    RegistroRendimientoDao rrdao;
    
    @Autowired
    DeportistaDao ddao;
    
    @Autowired
    TipoMetricaDao tmdao;

    @Override
    public List<RegistroRendimiento> getRendimientoDeportista(Long deportistaid) {
        return rrdao.findByDeportistaId(deportistaid);
    }

    @Override
    public RegistroRendimiento add(RegistroRendimientoDTO rdto) {
        Deportista d = ddao.findById(rdto.getIddeportista())
                .orElseThrow(() -> new RuntimeException("Deportista no encontrado"));
        TipoMetrica m = tmdao.findById(rdto.getIdmetrica())
                .orElseThrow(() -> new RuntimeException("Deportista no encontrado"));
        RegistroRendimiento registro = new RegistroRendimiento();
        registro.setComentarios(rdto.getComentarios());
        registro.setDeportista(d);
        registro.setFecha(rdto.getFecha());
        registro.setMetrica(m);
        registro.setValor(rdto.getValor());
        return rrdao.save(registro);
    }

    @Override
    public void delete(Long id) throws IOException {
       rrdao.deleteById(id);
    }
    
}
