package mx.com.gm.service;

import mx.com.gm.domain.Mensaje;
import mx.com.gm.dto.ChatUpdateDTO;
import mx.com.gm.dto.MensajeLeidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    @Autowired
    private  SimpMessagingTemplate messagingTemplate;


    public void notificarNuevoMensaje(Mensaje mensaje) {
        messagingTemplate.convertAndSend(
            "/topic/chat/" + mensaje.getChat().getId(), 
            mensaje
        );
        messagingTemplate.convertAndSend(
            "/topic/chats/updates",
            new ChatUpdateDTO(
                mensaje.getChat().getId(),
                mensaje.getContenido(),
                mensaje.getFechaEnvio(),
                mensaje.getRemitenteTipo(),
                mensaje.getRemitenteId()
            )
        );
    }

    public void notificarMensajeLeido(Long chatId, Long usuarioId) {
        messagingTemplate.convertAndSend(
            "/topic/chat/" + chatId + "/read",
            new MensajeLeidoDTO(chatId, usuarioId)
        );
    }
}

