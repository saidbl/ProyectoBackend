package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.ObjetivoRendimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjetivoRendimientoDao extends JpaRepository<ObjetivoRendimiento,Long>{
    List<ObjetivoRendimiento> findByDeportistaId(Long deportistaId);
    
    List<ObjetivoRendimiento> findByDeportistaIdAndCompletadoFalse(Long deportistaId);
    
}
