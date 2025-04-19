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
    
    
    @Query(value = 
        "SELECT r.id AS rutina_id, r.nombre AS rutina_nombre, r.dia, r.descripcion AS rutina_desc, " +
        "r.nivel_dificultad, r.objetivo, r.duracion_esperada, " +
        "e.id AS ejercicio_id, e.nombre AS ejercicio_nombre, e.descripcion AS ejercicio_desc, " +
        "e.series, e.repeticiones, e.descanso, e.orden, " +
        "re.id AS recurso_id, re.tipo AS recurso_tipo, re.url AS recurso_url, re.descripcion AS recurso_desc " +
        "FROM rutina r " +
        "JOIN rutina_jugador rj ON r.id = rj.id_rutina " +
        "LEFT JOIN ejercicio_rutina e ON r.id = e.id_rutina " +
        "LEFT JOIN recurso_rutina re ON e.id = re.id_ejercicio_rutina " +
        "WHERE rj.id_jugador = :deportistaId " +
        "ORDER BY r.dia, e.orden", 
        nativeQuery = true)
    List<Object[]> findRutinasCompletasByDeportistaIdNative(@Param("deportistaId") Long deportistaId);
    
    @Query(value = 
        "SELECT r.id AS rutina_id, r.nombre AS rutina_nombre, r.dia, r.descripcion AS rutina_desc, " +
        "r.nivel_dificultad, r.objetivo, r.duracion_esperada, " +
        "e.id AS ejercicio_id, e.nombre AS ejercicio_nombre, e.descripcion AS ejercicio_desc, " +
        "e.series, e.repeticiones, e.descanso, e.orden, " +
        "re.id AS recurso_id, re.tipo AS recurso_tipo, re.url AS recurso_url, re.descripcion AS recurso_desc " +
        "FROM rutina r " +
        "JOIN rutina_jugador rj ON r.id = rj.id_rutina " +
        "LEFT JOIN ejercicio_rutina e ON r.id = e.id_rutina " +
        "LEFT JOIN recurso_rutina re ON e.id = re.id_ejercicio_rutina " +
        "WHERE rj.id_jugador = :deportistaId AND r.dia = :diaSemana " +
        "ORDER BY r.dia, e.orden", 
        nativeQuery = true)
    List<Object[]> findRutinasCompletasByDeportistaIdAndDia(@Param("deportistaId") Long deportistaId, @Param("diaSemana") String diaSemana);
}
