package mx.com.gm.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProgresoObjetivoDTO {
    private Long id;
    private String nombreObjetivo;
    private String unidad;
    private Double valorActual;
    private Double valorObjetivo;
    private int porcentajeCompletado;
    private LocalDate fechaObjetivo;
    private boolean completado;
}
