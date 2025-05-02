package mx.com.gm.dto;

import lombok.Data;

@Data
public class TipoMetricaDTO {
    private Long id;
    private String nombre;
    private String unidad;
    private Long iddeporte;
    private boolean esObjetivo;
    private Long iddeportista;
}
