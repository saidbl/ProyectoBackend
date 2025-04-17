package mx.com.gm.dto;

import java.util.List;
import lombok.Data;

@Data
public class EjercicioRutinaDTO {
    private Long id;

    private Long idrutina;

    private String nombre;

    private String descripcion;

    private Integer series;

    private String repeticiones;

    private String descanso; 

    private Integer orden;
    
    private List<RecursoDTO> recursos;
}
