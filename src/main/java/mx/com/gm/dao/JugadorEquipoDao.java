package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.JugadorEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorEquipoDao extends JpaRepository<JugadorEquipo,Long>{
    List<JugadorEquipo> findByEquipoId(Long idequipo);
}
