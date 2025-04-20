package mx.com.gm.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;

@Data
public class EventoDTO {
    private Long id;
    private String nombre;
    private Long idOrganizacion;
    private Long idDeporte;
    private Integer numMaxEquipos;
    private LocalDate fecha;
    private LocalDate fechaFin;
    private String descripcion;
    private String ubicacion;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;
    private String contactoOrganizador;
    private Boolean recurrente;
    private String frecuencia;
    private List<String> diasSemana;
    private Boolean excluirFines;
    private List<EventoFechaDTO> fechas;
    private Long equiposInscritos;
}
