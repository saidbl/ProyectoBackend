package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.EjercicioRutinaDao;
import mx.com.gm.dao.RutinaDao;
import mx.com.gm.domain.EjercicioRutina;
import mx.com.gm.domain.Rutina;
import mx.com.gm.dto.EjercicioRutinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EjercicioRutinaServiceImpl implements EjercicioRutinaService {
    @Autowired
    EjercicioRutinaDao edao;
    
    @Autowired 
    RutinaDao rdao;

    @Override
    public List<EjercicioRutina> listarPorIdInstructor(Long idInstructor) {
        return edao.findByInstructorId(idInstructor);
    }

    @Override
    public EjercicioRutina add(EjercicioRutinaDTO erdto) {
        EjercicioRutina er = new EjercicioRutina();
        Rutina r =  rdao.findById(erdto.getIdrutina())
                 .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        er.setNombre(erdto.getNombre());
        er.setDescripcion(erdto.getDescripcion());
        er.setOrden(erdto.getOrden());
        er.setRutina(r);
        er.setSeries(erdto.getSeries());
        er.setRepeticiones(erdto.getRepeticiones());
        er.setDescanso(erdto.getDescanso());
        
        return edao.save(er);
    }

    @Override
    public void delete(Long er) {
        edao.deleteById(er);
    }

    @Override
    public EjercicioRutina edit(Long id, EjercicioRutinaDTO edto) {
       EjercicioRutina er = edao.findById(id)
               .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
       Rutina r = rdao.findById(edto.getIdrutina())
               .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
       er.setDescanso(edto.getDescanso());
       er.setDescripcion(edto.getDescripcion());
       er.setNombre(edto.getNombre());
       er.setOrden(edto.getOrden());
       er.setRepeticiones(edto.getRepeticiones());
       er.setRutina(r);
       er.setSeries(edto.getSeries());
       return edao.save(er);
    }

    


    
}
