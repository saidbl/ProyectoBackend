package mx.com.gm.dto;

import lombok.Data;

@Data
public class MedicionDTO {
    private String fecha;
    private Double peso;
    private Integer estatura;
    private Double porcentajeGrasa;
    private Double masaMuscular;
    private Double circunferenciaBrazo;
    private Double circunferenciaCintura;
    private Double circunferenciaCadera;
    private String presionArterial;
    private Long frecuenciaCardiacaReposo;
    private String notas;
}
