package mx.com.gm.service;
import java.util.List;
import mx.com.gm.domain.Chat;
import mx.com.gm.domain.Mensaje;
import mx.com.gm.dto.ChatRequest;
import mx.com.gm.dto.MensajeDTO;


public interface ChatService {
    public Chat crearChatIndividual(ChatRequest request) ;
    public List<Chat> obtenerChatsUsuario(Long userId, String tipoUsuario);
    public Chat obtenerChatPorId(Long chatId);
    public Mensaje guardarMensajeDesdeDTO(MensajeDTO dto);
}
