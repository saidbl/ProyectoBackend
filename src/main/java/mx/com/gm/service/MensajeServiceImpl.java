
package mx.com.gm.service;

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
        mensaje.setContenido(request.getContenido());
        mensaje.setRemitenteTipo(request.getRemitenteTipo());
        mensaje.setRemitenteId(request.getRemitenteId());
        Mensaje mensajeGuardado = mdao.save(mensaje);
        
        messagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), convertirAResponse(mensajeGuardado));
        return mensajeGuardado;
    }

    @Override
    public void marcarMensajesComoLeidos(Long chatId, Long usuarioId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
