package mx.com.gm.dto;

import java.util.Date;
import lombok.Data;

@Data
public class EquipoDTO {
    private Long id;
    private String nombre;
    private Long idinstructor;
    private Long iddeporte;
    private Integer maxJugadores;
    private Date fechaCreacion;
    private String estado;
    private String categoria;
    private Integer jugadoresAsociados;
}
