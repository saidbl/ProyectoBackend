package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.PosicionDao;
import mx.com.gm.domain.Posicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosicionServiceImpl implements PosicionService{
    @Autowired
    PosicionDao pdao;

    @Override
    public List<Posicion> listByDepId(Long id) {
        return pdao.findByDeporteId(id);
    }
    
}
