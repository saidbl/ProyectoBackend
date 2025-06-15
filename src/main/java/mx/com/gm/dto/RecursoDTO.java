package mx.com.gm.dto;

import lombok.Data;

@Data
public class RecursoDTO {
    private Long id;
    private String tipo; 
    private String url;
    private String descripcion;
}
