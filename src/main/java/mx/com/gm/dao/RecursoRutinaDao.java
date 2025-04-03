
package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.RecursoRutina;
import mx.com.gm.dto.TipoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecursoRutinaDao extends JpaRepository<RecursoRutina,Long>{
    public List<RecursoRutina> findByEjercicioRutinaId( Long ejercicioId);
    public List<RecursoRutina> findByEjercicioRutinaIdAndTipo(Long ejercicioId, TipoRecurso tipo);
    public boolean existsByEjercicioRutinaId (Long id);
}
