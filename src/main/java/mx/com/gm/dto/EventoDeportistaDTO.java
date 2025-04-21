package mx.com.gm.dto;

import java.util.List;
import lombok.Data;

@Data
public class EventoDeportistaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String estado;
    private String nombreOrganizador;
    private String deporte;
    private List<FechaEventoDTO> fechas;
}
