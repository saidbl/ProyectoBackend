package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.JugadorEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JugadorEquipoDao extends JpaRepository<JugadorEquipo,Long>{
    List<JugadorEquipo> findByEquipoId(Long idequipo);
     @Query("SELECT je.deportista FROM JugadorEquipo je WHERE je.equipo.id = :equipoId")
    List<Deportista> findDeportistasByEquipoId(@Param("equipoId") Long equipoId);
}
