package mx.com.gm.dto;

import java.util.Map;
import lombok.Data;

@Data
public class CumplimientoRutinasDTO {
    private long totalRutinas;
    private long completadas;
    private long incompletas;
    private int porcentajeCompletadas;
    private Map<String, Long> rutinasPorDia; 
}
