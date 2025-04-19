package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
@Entity
@Table(name = "checkin_rutina", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_rutina", "fecha", "id_jugador"}))
public class CheckinRutina implements Serializable{
    
    private static final long serialVersionUID = 1L;

    public enum EstadoCheckin {
        COMPLETADA, INCOMPLETA, PENDIENTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_rutina", nullable = false)
    private Rutina rutina;

    @ManyToOne
    @JoinColumn(name = "id_jugador", nullable = false)
    private Deportista jugador;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('COMPLETADA', 'INCOMPLETA', 'PENDIENTE')")
    private EstadoCheckin estado = EstadoCheckin.PENDIENTE;

    @Column(columnDefinition = "TEXT")
    private String comentarios;

    private LocalTime hora;
    
}
