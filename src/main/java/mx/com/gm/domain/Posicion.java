package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "posicion")
public class Posicion implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "id_deporte", referencedColumnName = "id") // Hace referencia a la columna en la BD
    private Deporte deporte;
}
