
package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.EjercicioRutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EjercicioRutinaDao extends JpaRepository<EjercicioRutina,Long> {
    @Query("SELECT er FROM EjercicioRutina er " +
           "JOIN er.rutina r " +
           "JOIN r.posicion p " +
           "JOIN r.instructor i " +
           "WHERE i.id = :instructorId")
    List<EjercicioRutina> findByInstructorId(@Param("instructorId") Long instructorId);
}
