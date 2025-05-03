package mx.com.gm.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mx.com.gm.domain.RegistroRendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RegistroRendimientoDao extends JpaRepository<RegistroRendimiento,Long> {
    List<RegistroRendimiento> findByDeportistaIdAndMetricaIdOrderByFecha(Long deportistaId, Long metricaId);
    
    @Query("SELECT r FROM RegistroRendimiento r WHERE r.deportista.id = :deportistaId AND r.fecha BETWEEN :startDate AND :endDate")
    List<RegistroRendimiento> findByDeportistaAndFechaBetween(
        @Param("deportistaId") Long deportistaId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    List<RegistroRendimiento> findByDeportistaId(Long deportistaid);
    
    @Query("SELECT r FROM RegistroRendimiento r WHERE r.deportista.id = :deportistaId AND r.metrica.id = :metricaId ORDER BY r.fecha DESC LIMIT 1")
    Optional<RegistroRendimiento> findUltimoRegistro(
            @Param("deportistaId") Long deportistaId,
            @Param("metricaId") Long metricaId);
}
