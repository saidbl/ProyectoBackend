
package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.RecursoRutina;
import mx.com.gm.dto.TipoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RecursoRutinaDao extends JpaRepository<RecursoRutina,Long>{
    @Query("SELECT rr FROM RecursoRutina rr " +
           "JOIN rr.ejercicioRutina er " +
           "JOIN er.rutina r " +
           "JOIN r.instructor i " +
           "WHERE i.id = :instructorId")
    List<RecursoRutina> findByInstructorId(@Param("instructorId") Long instructorId);
    public List<RecursoRutina> findByEjercicioRutinaId( Long ejercicioId);
    public List<RecursoRutina> findByEjercicioRutinaIdAndTipo(Long ejercicioId, TipoRecurso tipo);
    public boolean existsByEjercicioRutinaId (Long id);
}
