package mx.com.gm.dao;
import java.util.List;
import java.util.Optional;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeportistaDao extends JpaRepository<Deportista,Long>{
    Optional<Deportista> findByEmail(String email);
    List<Deportista> findByInstructorId(Long idInstructor);
    @Query("SELECT r FROM Rutina r " +
           "JOIN RutinaJugador rj ON r.id = rj.rutina.id " +
           "WHERE rj.deportista.id = :idDeportista AND r.dia = :diaSemana")
    List<Rutina> findRutinasByDeportistaAndDiaSemana(
            @Param("idDeportista") Long idDeportista, 
            @Param("diaSemana") String diaSemana);
}
