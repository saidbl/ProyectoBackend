package mx.com.gm.service;

import java.time.LocalDate;
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
    public List<RegistroRendimiento> getRendimientoDeportista(Long deportistaid, String periodo) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1); 
        if (periodo != null) {
            switch (periodo) {
                case "3m" -> startDate = endDate.minusMonths(3);
                case "6m" -> startDate = endDate.minusMonths(6);
                case "1y" -> startDate = endDate.minusYears(1);
                case "all" -> startDate = null;
            }
        }
        List<RegistroRendimiento> registros;
        if (startDate != null) {
            registros = rrdao.findByDeportistaAndFechaBetween(deportistaid, startDate, endDate);
        } else {
            registros = rrdao.findByDeportistaId(deportistaid);
        }
        return registros;
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
    
}
