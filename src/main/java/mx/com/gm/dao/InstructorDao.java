
package mx.com.gm.dao;

import java.util.Optional;
import mx.com.gm.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InstructorDao extends JpaRepository<Instructor,Long>{
    Optional<Instructor> findByEmail(String email);
}
