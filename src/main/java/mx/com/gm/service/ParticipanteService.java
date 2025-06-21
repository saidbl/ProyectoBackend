
package mx.com.gm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.com.gm.dao.ChatParticipanteDao;
import mx.com.gm.dao.EquipoDao;
import mx.com.gm.domain.Chat;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.RemitenteTipo;
import mx.com.gm.dto.MensajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipanteService {
    @Autowired
    private ChatParticipanteDao chatParticipanteRepository;
    @Autowired
    private EquipoDao edao;


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
  public Map<Long, String> obtenerParticipantesConRol(Chat chat,MensajeDTO dto) {
    Map<Long, String> participantes = new HashMap<>();
      System.out.println(chat);
      System.out.println(chat.getDeportista());
    if (chat.getDeportista() != null) {
        if(dto.getRemitenteTipo()!= RemitenteTipo.DEPORTISTA){
        System.out.println("Entra al if");
        System.out.println(chat.getDeportista());
        participantes.put(chat.getDeportista().getId(), "deportista");
        System.out.println(participantes);
        }
    }
    if (chat.getInstructor() != null) {
        if(dto.getRemitenteTipo()!=RemitenteTipo.INSTRUCTOR){
        participantes.put(chat.getInstructor().getId(), "instructor");
        }
    }
    if(chat.getOrganizacion()!=null){
        if(dto.getRemitenteTipo()!=RemitenteTipo.ORGANIZACION){
        participantes.put(chat.getOrganizacion().getId(), "organizacion");
        }
    }

    if(chat.getEquipo()!=null) {
    List<Deportista> deportistas = edao.findJugadoresByEquipoId(chat.getEquipo().getId());
     if (chat.getDeportista() != null) {
      System.out.println(deportistas);
        for (Deportista d : deportistas) {
        if(!(d.equals(chat.getDeportista()))){
            participantes.put(chat.getDeportista().getId(), "deportista");
        }
        }
      System.out.println(participantes);
     }else{
         System.out.println(deportistas);
        for (Deportista d : deportistas) {
            participantes.put(d.getId(), "deportista");
        }
      System.out.println(participantes);
     }
    }

    return participantes;
}

}
