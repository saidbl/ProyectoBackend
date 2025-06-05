package mx.com.gm.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mx.com.gm.domain.MedicionFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MedicionFisicaDao extends JpaRepository<MedicionFisica,Long>{
    @Query("SELECT m FROM MedicionFisica m WHERE m.deportista.id = :deportistaId AND m.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY m.fecha ASC")
    List<MedicionFisica> findByDeportistaAndRangoFechas(
            @Param("deportistaId") Long deportistaId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );
    Optional<MedicionFisica> findFirstByDeportistaIdOrderByFechaDesc(Long deportistaId);
    
    List<MedicionFisica> findByDeportistaId(@Param("deportistaId") Long deportistaId);
}
