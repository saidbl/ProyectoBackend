package mx.com.gm.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class EventoFechaDTO {
    private Long id;
    private Long idEvento;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;
    private String motivoCancelacion;
}
