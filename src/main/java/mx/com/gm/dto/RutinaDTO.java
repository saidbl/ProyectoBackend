
package mx.com.gm.dto;

import java.util.List;
import lombok.Data;

@Data
public class RutinaDTO {
    private Long id;
    private String nombre;
    private String dia;
    private Long idInstructor;
    private Long idPosicion;
    private String descripcion;
    private String nivel_dificultad;
    private String objetivo;
    private Long duracion_esperada;
    private List<EjercicioRutinaDTO> ejercicios;
}
