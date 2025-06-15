package mx.com.gm.dto;

import java.time.LocalDateTime;
import lombok.Data;
import mx.com.gm.domain.RemitenteTipo;

@Data
public class MensajeDTO {

    public MensajeDTO() {
    }
    private Long id;
    private Long idChat;
    private String contenido;
    private RemitenteTipo remitenteTipo;
    private Long remitenteId;
    private LocalDateTime fechaEnvio;
    private boolean leido;

    public MensajeDTO(Long idChat, Long remitenteId) {
        this.idChat = idChat;
        this.remitenteId = remitenteId;
    }

    public MensajeDTO(Long id, String contenido, RemitenteTipo remitenteTipo, Long remitenteId, LocalDateTime fechaEnvio, boolean leido) {
        this.id = id;
        this.contenido = contenido;
        this.remitenteTipo = remitenteTipo;
        this.remitenteId = remitenteId;
        this.fechaEnvio = fechaEnvio;
        this.leido = leido;
    }
    
}
