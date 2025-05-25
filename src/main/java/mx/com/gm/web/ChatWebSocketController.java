package mx.com.gm.web;

import mx.com.gm.domain.Mensaje;
import mx.com.gm.domain.MensajeRequest;
import mx.com.gm.dto.MensajeDTO;
import mx.com.gm.service.ChatService;
import mx.com.gm.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    @Autowired
    private MensajeService mservice;
    @Autowired
    private ChatService chservice;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/chat/{chatId}/send")
    public void handleMessage(@Payload MensajeRequest request, 
                            @DestinationVariable Long chatId) {
        mservice.enviarMensaje(request);
    }
    
    @MessageMapping("/chat.send") 
    public void enviarMensaje(@Payload MensajeDTO mensajeDTO) {
        Mensaje mensajeGuardado = chservice.guardarMensajeDesdeDTO(mensajeDTO);
        messagingTemplate.convertAndSendToUser(
            mensajeDTO.getIdChat().toString(), 
            "/queue/messages", 
            mensajeGuardado
        );
    }
            
}
