
package mx.com.gm.dao;

import java.util.Optional;
import mx.com.gm.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminDao extends JpaRepository<Admin, Integer>{
    Optional<Admin> findByEmail(String email);
    boolean existsByEmail(String email);
}
