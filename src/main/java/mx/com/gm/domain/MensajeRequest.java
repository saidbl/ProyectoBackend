
package mx.com.gm.domain;

import lombok.Data;

@Data
public class MensajeRequest {
    private Long chatId;

    private String contenido;

    private RemitenteTipo remitenteTipo;

    private Long remitenteId;
    
}
