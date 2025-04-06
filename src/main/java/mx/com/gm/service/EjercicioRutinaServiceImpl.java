package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.EjercicioRutinaDao;
import mx.com.gm.domain.EjercicioRutina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EjercicioRutinaServiceImpl implements EjercicioRutinaService {
    @Autowired
    EjercicioRutinaDao edao;

    @Override
    public List<EjercicioRutina> listarPorIdInstructor(Long idInstructor) {
        return edao.findByInstructorId(idInstructor);
    }
    
}
