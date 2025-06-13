
package mx.com.gm.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import mx.com.gm.dao.MensajeDao;
import mx.com.gm.domain.Chat;
import mx.com.gm.domain.Mensaje;
import mx.com.gm.domain.MensajeRequest;
import mx.com.gm.dto.MensajeDTO;
import mx.com.gm.dto.MensajeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MensajeServiceImpl implements MensajeService{
    @Autowired 
    private ChatService chservice;
    
    @Autowired
    private ParticipanteService pservice;
    
    @Autowired
    private MensajeDao mdao;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MensajeMapper mmapper;

    @Override
    public Page<MensajeDTO> obtenerMensajesPaginados(Long chatId, Pageable pageable) {
        Page<Mensaje> paginaMensajes = mdao.findByChatIdOrderByFechaEnvioDesc(chatId, pageable);
        return paginaMensajes.map(mmapper::toDto);
    }

    @Override
    public Mensaje enviarMensaje(MensajeRequest request) {
        Chat chat = chservice.obtenerChatPorId(request.getChatId());
        pservice.validarRemitenteEnChat(chat, request.getRemitenteId(), request.getRemitenteTipo());
        Mensaje mensaje = new Mensaje();
        mensaje.setChat(chat);
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensaje.setContenido(request.getContenido());
        mensaje.setRemitenteTipo(request.getRemitenteTipo());
        mensaje.setRemitenteId(request.getRemitenteId());
        mensaje.setLeido(false);
        Mensaje mensajeGuardado = mdao.save(mensaje);
        MensajeDTO dto = convertirAResponse(mensajeGuardado);

    messagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), dto);
        
        messagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), convertirAResponse(mensajeGuardado));
        return mensajeGuardado;
    }

    @Override
        @Transactional
public void marcarMensajesComoLeidos(Long chatId, Long usuarioId) {
    mdao.marcarMensajesComoLeidosEnChat(chatId);
    messagingTemplate.convertAndSend("/topic/chat/" + chatId + "/read", new MensajeDTO(chatId, usuarioId));
}
    
    private MensajeDTO convertirAResponse(Mensaje mensaje) {
        return new MensajeDTO(
            mensaje.getId(),
            mensaje.getContenido(),
            mensaje.getRemitenteTipo(),
            mensaje.getRemitenteId(),
            mensaje.getFechaEnvio(),
            mensaje.isLeido()
        );
    }

   
}
