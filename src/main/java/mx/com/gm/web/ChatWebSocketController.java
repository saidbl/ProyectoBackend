package mx.com.gm.web;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.com.gm.domain.Chat;
import mx.com.gm.domain.Mensaje;
import mx.com.gm.domain.MensajeRequest;
import mx.com.gm.domain.RemitenteTipo;
import mx.com.gm.dto.MensajeDTO;
import mx.com.gm.service.ChatService;
import mx.com.gm.service.MensajeService;
import mx.com.gm.service.ParticipanteService;
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

    @Autowired
    private ParticipanteService pservice;
    
    @MessageMapping("/chat/{chatId}/send")
    public void handleMessage(@Payload MensajeRequest request, 
                            @DestinationVariable Long chatId) {
        mservice.enviarMensaje(request);
    }
    
    @MessageMapping("/chat.send") 
    public void enviarMensaje(@Payload MensajeDTO mensajeDTO) {
        Chat chat = chservice.obtenerChatPorId(mensajeDTO.getIdChat());
        Mensaje mensajeGuardado = chservice.guardarMensajeDesdeDTO(mensajeDTO);
        MensajeDTO dto = convertirADTO(mensajeGuardado); 
        messagingTemplate.convertAndSend("/topic/chats/updates", mensajeDTO);
        messagingTemplate.convertAndSend("/topic/mensajes", "nuevo");
        messagingTemplate.convertAndSendToUser(
        mensajeDTO.getRemitenteId().toString(), 
        "/topic/notificaciones", 
        mensajeDTO
    );
    
    messagingTemplate.convertAndSend("/topic/chat/" + mensajeDTO.getIdChat(), mensajeDTO);
    Map<Long, String>  participantes = pservice.obtenerParticipantesConRol(chat);
    String remitenteRol = mensajeDTO.getRemitenteTipo().getNombre().toLowerCase();
    Long remitenteId = mensajeDTO.getRemitenteId();
    for (Map.Entry<Long, String> entry : participantes.entrySet()) {
        Long participanteId = entry.getKey();
        String rol = entry.getValue().toLowerCase();

        boolean mismoId = participanteId.equals(remitenteId);
        boolean mismoRol = rol.equals(remitenteRol);

        if (!mismoId || (mismoId && !mismoRol)) {
            messagingTemplate.convertAndSend("/topic/notificaciones/" + rol + "/" + participanteId, dto);
            System.out.println("Enviando notificación a usuario: " + participanteId + " (" + rol + ") -> " + dto);
        }
    }
    }
    public MensajeDTO convertirADTO(Mensaje mensaje){
        MensajeDTO dto = new MensajeDTO();
        dto.setContenido(mensaje.getContenido());
        dto.setFechaEnvio(mensaje.getFechaEnvio());
        dto.setId(mensaje.getId());
        dto.setIdChat(mensaje.getChat().getId());
        dto.setLeido(mensaje.isLeido());
        dto.setRemitenteId(mensaje.getRemitenteId());
        dto.setRemitenteTipo(mensaje.getRemitenteTipo());
        return dto;
    }
            
}
