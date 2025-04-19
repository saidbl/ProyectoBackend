package mx.com.gm.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class CheckinRutinaDTO {
    private Integer id;
    private Long idrutina;
    private Long idjugador;
    private LocalDate fecha;
    private String estado;
    private String comentarios;
    private LocalTime hora;
}
