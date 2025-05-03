package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
@Table(name = "medicion_fisica")
public class MedicionFisica implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_deportista", nullable = false)
    private Deportista deportista;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    private Double peso; 
    private Integer estatura;
    private Double imc;
    private Double porcentajeGrasa;
    private Double masaMuscular;
    private Double circunferenciaBrazo; 
    private Double circunferenciaCintura; 
    private Double circunferenciaCadera;
    @PrePersist
    @PreUpdate
    private void calcularIMC() {
        if (peso != null && estatura != null && estatura > 0) {
            this.imc = peso / Math.pow(estatura / 100.0, 2);
        }
    }
}

