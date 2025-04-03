
package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dao.InstructorDao;
import mx.com.gm.dao.PosicionDao;
import mx.com.gm.dao.RutinaDao;
import mx.com.gm.domain.Instructor;
import mx.com.gm.domain.Posicion;
import mx.com.gm.domain.Rutina;
import mx.com.gm.dto.RutinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutinaServiceImpl implements RutinaService{
    @Autowired
    private RutinaDao rdao;
    @Autowired
    private InstructorDao idao;

    @Autowired
    private PosicionDao pdao;

    @Override
    public List<Rutina> listbyInstId(Long id) {
        return rdao.findByInstructorId(id);
    }

    @Override
    public void delete(Long id) {
        rdao.deleteById(id);
    }

    @Override
    public Rutina add(RutinaDTO rutinaDTO) {
         Instructor instructor = idao.findById(rutinaDTO.getIdInstructor())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        Posicion posicion = pdao.findById(rutinaDTO.getIdPosicion())
                .orElseThrow(() -> new RuntimeException("Posición no encontrada"));

        Rutina rutina = new Rutina();
        rutina.setNombre(rutinaDTO.getNombre());
        rutina.setNivel_dificultad(rutinaDTO.getNivel_dificultad());
        rutina.setDia(rutinaDTO.getDia());
        rutina.setDescripcion(rutinaDTO.getDescripcion());
        rutina.setInstructor(instructor);
        rutina.setPosicion(posicion);
        rutina.setDuracion_esperada(rutinaDTO.getDuracion_esperada());
        rutina.setObjetivo(rutinaDTO.getObjetivo());

        return rdao.save(rutina);
    }

    @Override
    public Long getTotalRutinasByInstructorId(Long instructorId) {
        return rdao.countByInstructorId(instructorId);
    }

    @Override
    public List<Rutina> getTop3RutinasPopularesPorInstructor(Long instructorId) {
        return rdao.findTop3RutinasForInstructor(instructorId);
    }
    
}
