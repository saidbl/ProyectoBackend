package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "objetivo_rendimiento")
public class ObjetivoRendimiento implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_deportista", nullable = false)
    private Deportista deportista;
    @ManyToOne
    @JoinColumn(name = "id_metrica", nullable = false)
    private TipoMetrica metrica;
    private Double valorObjetivo;
    private LocalDate fechaObjetivo;
    private LocalDate fechaEstablecido;
    private boolean completado;
    private Integer prioridad;
}
