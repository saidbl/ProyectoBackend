package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RutinaDao extends JpaRepository<Rutina,Long>{
    List<Rutina> findByInstructorId(Long instructorId);
    
    @Query("SELECT COUNT(r) FROM Rutina r WHERE r.instructor.id = :instructorId")
    Long countByInstructorId(@Param("instructorId") Long instructorId);
    
    @Query(value = """
        WITH rutinas_ordenadas AS (
            SELECT 
                r.*,
                COUNT(rj.id_jugador) AS total_deportistas,
                ROW_NUMBER() OVER (ORDER BY COUNT(rj.id_jugador) DESC) AS ranking_popularidad,
                ROW_NUMBER() OVER (ORDER BY r.nombre ASC) AS ranking_nombre
            FROM 
                rutina r
            LEFT JOIN 
                rutina_jugador rj ON r.id = rj.id_rutina
            WHERE 
                r.id_instructor = :instructorId
            GROUP BY 
                r.id
        )
        SELECT * FROM rutinas_ordenadas
        ORDER BY 
            CASE 
                WHEN ranking_popularidad <= 3 THEN ranking_popularidad
                ELSE ranking_nombre + 1000
            END
        LIMIT 3
        """, nativeQuery = true)
    List<Rutina> findTop3RutinasForInstructor(@Param("instructorId") Long instructorId);
    
     @Query("SELECT DISTINCT r FROM Rutina r " +
           "JOIN FETCH r.ejercicios e " +
           "LEFT JOIN FETCH e.recursos re " +
           "JOIN r.deportistas d " +
           "WHERE d.id = :deportistaId " +
           "ORDER BY r.dia, e.orden")
    List<Rutina> findRutinasCompletasByDeportistaId(@Param("deportistaId") Long deportistaId);
}
