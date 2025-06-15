package mx.com.gm.dto;

import lombok.Data;

@Data
public class MensajeLeidoDTO {
    private Long chatId;
    private Long usuarioId;

    public MensajeLeidoDTO(Long chatId, Long usuarioId) {
        this.chatId = chatId;
        this.usuarioId = usuarioId;
    }
    
}
