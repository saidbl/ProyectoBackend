
package mx.com.gm.dto;

import lombok.Data;
import mx.com.gm.domain.ChatTipo;

@Data
public class ChatRequest {
    private ChatTipo tipo;
    private Long instructorId; 
    private Long deportistaId; 
    private Long organizacionId; 
    private Long equipoId; 
    private Long deporteId; 
}
