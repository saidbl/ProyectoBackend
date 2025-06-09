package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class Mensaje implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Chat chat;

    private String contenido;

    @Enumerated(EnumType.STRING)
    private RemitenteTipo remitenteTipo;

    private Long remitenteId;

    private LocalDateTime fechaEnvio;
    private boolean leido= false;
}
