
package mx.com.gm.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
@AllArgsConstructor
public class ResumenAtletasDTO {
    private long totalMasculinos;
    private long totalFemeninos;
    private double edadPromedioGeneral;
    private double edadPromedioHombres;
    private double edadPromedioMujeres;
    private List<PosicionGeneroDTO> distribucionPosicionGenero;
}
