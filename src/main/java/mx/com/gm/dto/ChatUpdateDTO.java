
package mx.com.gm.dto;

import java.time.LocalDateTime;
import lombok.Data;
import mx.com.gm.domain.RemitenteTipo;

@Data
public class ChatUpdateDTO {
    private Long chatId;
    private String ultimoMensaje;
    private LocalDateTime fechaActualizacion;
    private RemitenteTipo remitenteTipo;
    private Long remitenteId;

    public ChatUpdateDTO(Long chatId, String ultimoMensaje, LocalDateTime fechaActualizacion, RemitenteTipo remitenteTipo, Long remitenteId) {
        this.chatId = chatId;
        this.ultimoMensaje = ultimoMensaje;
        this.fechaActualizacion = fechaActualizacion;
        this.remitenteTipo = remitenteTipo;
        this.remitenteId = remitenteId;
    }
    
    
}
