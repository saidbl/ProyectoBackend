package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EquipoDao extends JpaRepository<Equipo,Long>{
    List<Equipo> findByInstructorId(Long idInstructor);
}
