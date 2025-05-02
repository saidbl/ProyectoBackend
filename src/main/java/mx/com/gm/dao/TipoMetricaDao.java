package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.TipoMetrica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMetricaDao extends JpaRepository<TipoMetrica,Long>{
    List<TipoMetrica> findByDeportistaId(Long deportistaId);
}
