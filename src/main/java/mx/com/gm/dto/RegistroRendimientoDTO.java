package mx.com.gm.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RegistroRendimientoDTO {
    private Long id;
    private Long iddeportista;
    private Long idmetrica;
    private LocalDate fecha;
    private Double valor;
    private String comentarios;
}
