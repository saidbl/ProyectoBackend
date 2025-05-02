package mx.com.gm.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ObjetivoRendimientoDTO {
    private Long id;
    private Long iddeportista;
    private Long idmetrica;
    private Double valorObjetivo;
    private LocalDate fechaObjetivo;
    private LocalDate fechaEstablecido;
    private boolean completado;
    private Integer prioridad;
}
