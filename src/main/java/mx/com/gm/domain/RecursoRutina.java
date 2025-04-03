package mx.com.gm.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;
import mx.com.gm.dto.TipoRecurso;

@Entity
@Table(name = "recurso_rutina")
@Data
public class RecursoRutina implements Serializable{
    private static final long serialVersionUID = 1L;
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ejercicio_rutina", nullable = false)
    @JsonBackReference
    private EjercicioRutina ejercicioRutina;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRecurso tipo;

    @Column(nullable = false)
    private String url;

    private String descripcion;

}
