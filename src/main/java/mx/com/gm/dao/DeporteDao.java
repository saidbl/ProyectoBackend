package mx.com.gm.dao;

import mx.com.gm.domain.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeporteDao extends JpaRepository<Deporte,Long>{
    
}
