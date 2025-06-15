package mx.com.gm.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenCumplimientoDTO {
    double porcentajeCompletadas;
    double porcentajeIncompletas;
    List<DiaSemanaDTO> cumplimientoPorDia;
    List<AtletaPendienteDTO> topPendientes;
    
}
