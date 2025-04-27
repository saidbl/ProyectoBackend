package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.ObjetivoRendimientoDao;
import mx.com.gm.domain.ObjetivoRendimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjetivoRendimientoServiceImpl implements ObjetivoRendimientoService{
    @Autowired
    ObjetivoRendimientoDao ordao;

    @Override
    public List<ObjetivoRendimiento> getByDeportista(Long id) {
        return ordao.findByDeportistaId(id);
    }
    
}
