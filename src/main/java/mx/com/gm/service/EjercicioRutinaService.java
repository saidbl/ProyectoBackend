package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.EjercicioRutina;
import mx.com.gm.dto.EjercicioRutinaDTO;

public interface EjercicioRutinaService {
   public List<EjercicioRutina> listarPorIdInstructor(Long idInstructor);
   public EjercicioRutina add (EjercicioRutinaDTO erdto);
   public void delete(Long er);
   public EjercicioRutina edit(Long id, EjercicioRutinaDTO edto);
}
