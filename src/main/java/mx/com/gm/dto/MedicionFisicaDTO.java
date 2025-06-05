
package mx.com.gm.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MedicionFisicaDTO {
    private Long id;
    private Long idDeportista;
    private LocalDate fecha;
    private Double peso; 
    private Integer estatura;
    private Double imc;
    private Double porcentajeGrasa;
    private Double masaMuscular;
    private Double circunferenciaBrazo; 
    private Double circunferenciaCintura; 
    private Double circunferenciaCadera;
    private String presionArterial;
    private Long frecuenciaCardiacaReposo;
    private String notas;
}
