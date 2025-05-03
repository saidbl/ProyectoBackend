
package mx.com.gm.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EvolucionFisicaDTO {
    private LocalDate fecha;
    private Double peso;
    private Double imc;
    private Double masaMuscular;
    private Double porcentajeGrasa;
    private String clasificacionIMC;
}
