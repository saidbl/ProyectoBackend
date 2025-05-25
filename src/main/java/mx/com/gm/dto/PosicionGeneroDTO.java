package mx.com.gm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosicionGeneroDTO {
    private String posicion;
    private long hombres;
    private long mujeres;

}
