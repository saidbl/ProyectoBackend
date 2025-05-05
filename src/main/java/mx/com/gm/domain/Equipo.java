package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Entity
@Data
@Table(name = "equipo")
public class Equipo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_instructor", nullable = false)
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "id_deporte", nullable = false)
    private Deporte deporte;

    @Column(name = "max_jugadores", nullable = false)
    private int maxJugadores;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "categoria")
    private String categoria;
    
    @Column(name = "jugadores_asociados")
    private Integer jugadoresAsociados;
    
    private String img;
    
}
