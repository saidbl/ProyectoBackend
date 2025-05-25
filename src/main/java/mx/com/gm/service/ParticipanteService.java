
package mx.com.gm.service;

import mx.com.gm.dao.ChatParticipanteDao;
import mx.com.gm.domain.Chat;
import mx.com.gm.domain.RemitenteTipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipanteService {
    @Autowired
    private ChatParticipanteDao chatParticipanteRepository;


    public void validarRemitenteEnChat(Chat chat, Long remitenteId, RemitenteTipo tipo) {
        boolean esValido = false;

        switch (chat.getTipo()) {
            case INSTRUCTOR_DEPORTISTA -> esValido = validarChatIndividual(chat, remitenteId, tipo);
                
            case EQUIPO -> esValido = validarChatEquipo(chat, remitenteId, tipo);
                
            case ORGANIZACION_INSTRUCTOR -> esValido = validarChatOrganizacion(chat, remitenteId, tipo);
                
            default -> throw new IllegalArgumentException("Tipo de chat no soportado");
        }

        if (!esValido) {
            throw new SecurityException("Usuario no autorizado para enviar mensajes en este chat");
        }
    }

    private boolean validarChatIndividual(Chat chat, Long remitenteId, RemitenteTipo tipo) {
        return (tipo == RemitenteTipo.INSTRUCTOR && remitenteId.equals(chat.getInstructor().getId())) 
                || (tipo == RemitenteTipo.DEPORTISTA && remitenteId.equals(chat.getDeportista().getId()));
    }

    private boolean validarChatEquipo(Chat chat, Long remitenteId, RemitenteTipo tipo) {
        if (tipo != RemitenteTipo.DEPORTISTA) return false;
        
        return chatParticipanteRepository.existsByChatIdAndDeportistaId(
            chat.getId(), 
            remitenteId
        );
    }

    private boolean validarChatOrganizacion(Chat chat, Long remitenteId, RemitenteTipo tipo) {
        return (tipo == RemitenteTipo.ORGANIZACION && remitenteId.equals(chat.getOrganizacion().getId()))
                || (tipo == RemitenteTipo.INSTRUCTOR && remitenteId.equals(chat.getInstructor().getId()));
    }
}
