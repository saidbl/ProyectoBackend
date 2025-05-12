package mx.com.gm.dto;

import java.util.List;
import lombok.Data;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.ObjetivoRendimiento;
import mx.com.gm.domain.RegistroRendimiento;

@Data
public class DeportistaRendimiento {
    private Deportista deportista;
    private List<RegistroRendimiento> registrosRendimiento;
    private List<ObjetivoRendimiento> objetivosIncompletos;
    private List<ObjetivoRendimiento> objetivosTotales;
    private Long rutinasCompletadas;
    private Long totalRutinas;
    private List<EvolucionFisicaDTO> evolucionFisica;
    private List<ProgresoObjetivoDTO> progresoObjetivo;
}
