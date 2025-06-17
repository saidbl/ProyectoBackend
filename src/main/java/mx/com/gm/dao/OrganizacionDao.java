package mx.com.gm.dao;

import java.util.List;
import java.util.Optional;
import mx.com.gm.domain.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizacionDao extends JpaRepository<Organizacion,Long>{
       Optional<Organizacion> findByEmail(String email);
       List<Organizacion> findByDeporteId(Long id);
       boolean existsByEmail(String email);
}
