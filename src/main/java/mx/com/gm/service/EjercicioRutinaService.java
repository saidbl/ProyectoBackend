package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.EjercicioRutina;

public interface EjercicioRutinaService {
   public List<EjercicioRutina> listarPorIdInstructor(Long idInstructor);
}
