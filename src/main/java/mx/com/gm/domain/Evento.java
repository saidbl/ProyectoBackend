package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
@Entity
public class Evento implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_organizacion", nullable = false)
    private Organizacion organizacion;

    @ManyToOne
    @JoinColumn(name = "id_deporte", nullable = false)
    private Deporte deporte;

    @Column(name = "num_max_equipos", nullable = false)
    private Integer numMaxEquipos;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ubicacion", length = 255)
    private String ubicacion;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Column(name = "estado", length = 20)
    private String estado;
    
    @Column(name = "recurrente")
    private Boolean recurrente;
    
    private String frecuencia;
    
    @Column(name = "dias_semana")
    private String diasSemana;
    
    @Column(name = "excluir_fines")
    private Boolean excluirFines;

    @Column(name = "contacto_organizador", length = 255)
    private String contactoOrganizador;
    
    @Column(name = "equipos_inscritos")
    private Long equiposInscritos;
    
    private String imagen;
    
    public boolean esFuturo() {
        LocalDate hoy = LocalDate.now();
        return this.fecha.isAfter(hoy) || this.fecha.isEqual(hoy);
    }
}
