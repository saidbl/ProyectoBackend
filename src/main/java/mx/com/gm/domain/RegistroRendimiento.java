package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

@Entity 
@Data
@Table(name = "registro_rendimiento")
public class RegistroRendimiento implements Serializable{
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
    
    private LocalDate fecha;
    private Double valor;
    
    private String comentarios;
}
