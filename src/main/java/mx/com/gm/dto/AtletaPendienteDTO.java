package mx.com.gm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtletaPendienteDTO {
    String nombre;
    Long pendientes;
}
