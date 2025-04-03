
package mx.com.gm.dto;

import lombok.Data;

@Data
public class RutinaDTO {
    private String nombre;
    private String dia;
    private Long idInstructor;
    private Long idPosicion;
    private String descripcion;
    private String nivel_dificultad;
    private String objetivo;
    private Long duracion_esperada;
}
