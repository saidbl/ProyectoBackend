
package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Rutina;
import mx.com.gm.dto.RutinaDTO;


public interface RutinaService {
    public List<Rutina> listbyInstId(Long id);
    void delete(Long id);
    public Rutina add(RutinaDTO r);
    public Long getTotalRutinasByInstructorId(Long instructorId);
    public List<Rutina> getTop3RutinasPopularesPorInstructor(Long instructorId);
}
