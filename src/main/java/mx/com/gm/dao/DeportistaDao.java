package mx.com.gm.dao;
import java.util.List;
import java.util.Optional;
import mx.com.gm.domain.Deportista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeportistaDao extends JpaRepository<Deportista,Long>{
    Optional<Deportista> findByEmail(String email);
    List<Deportista> findByInstructorId(Long idInstructor);

}
