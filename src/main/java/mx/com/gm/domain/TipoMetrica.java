package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Entity 
@Data
@Table(name = "tipo_metrica")
public class TipoMetrica implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String unidad;
    @ManyToOne
    @JoinColumn(name = "id_deporte", nullable = false)
    private Deporte deporte;
    private boolean esObjetivo;
    @ManyToOne
    @JoinColumn(name = "id_deportista", nullable = false)
    private Deportista deportista;
}
