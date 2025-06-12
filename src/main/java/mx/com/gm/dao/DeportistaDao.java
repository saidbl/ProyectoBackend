package mx.com.gm.dao;
import java.util.List;
import java.util.Optional;
import mx.com.gm.domain.Deportista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DeportistaDao extends JpaRepository<Deportista,Long>{
    Optional<Deportista> findByEmail(String email);
    List<Deportista> findByInstructorId(Long idInstructor);
    boolean existsByEmail(String email);
    @Query("SELECT COUNT(d) FROM Deportista d WHERE d.instructor.id = :id AND d.genero = 'Masculino'")
    long countMasculinosByInstructorId(@Param("id") Long instructorId);
    
    @Query("SELECT COUNT(d) FROM Deportista d WHERE d.instructor.id = :id AND d.genero = 'Femenino'")
    long countFemeninosByInstructorId(@Param("id") Long instructorId);
    
    @Query("SELECT AVG(YEAR(CURRENT_DATE) - YEAR(d.fechaNacimiento)) " +
           "FROM Deportista d WHERE d.instructor.id = :instructorId")
    Double getEdadPromedioGeneral(@Param("instructorId") Long instructorId);
    
    @Query("SELECT AVG(YEAR(CURRENT_DATE) - YEAR(d.fechaNacimiento)) " +
           "FROM Deportista d WHERE d.instructor.id = :instructorId AND d.genero = 'Masculino'")
    Double getEdadPromedioHombres(@Param("instructorId") Long instructorId);
    
    @Query("SELECT AVG(YEAR(CURRENT_DATE) - YEAR(d.fechaNacimiento)) " +
           "FROM Deportista d WHERE d.instructor.id = :instructorId AND d.genero = 'Femenino'")
    Double getEdadPromedioMujeres(@Param("instructorId") Long instructorId);
    @Query("SELECT p.nombre, d.genero, COUNT(d) " +
           "FROM Deportista d JOIN d.posicion p " +
           "WHERE d.instructor.id = :instructorId " +
           "GROUP BY p.nombre, d.genero")
    List<Object[]> getDistribucionPosicionGenero(@Param("instructorId") Long instructorId);
}
