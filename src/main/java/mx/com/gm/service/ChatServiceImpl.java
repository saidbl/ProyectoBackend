
package mx.com.gm.service;

import java.util.ArrayList;
import java.util.List;
import mx.com.gm.dao.ChatDao;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.EquipoDao;
import mx.com.gm.dao.InstructorDao;
import mx.com.gm.dao.JugadorEquipoDao;
import mx.com.gm.dao.MensajeDao;
import mx.com.gm.dao.OrganizacionDao;
import mx.com.gm.domain.Chat;
import mx.com.gm.domain.ChatParticipante;
import mx.com.gm.domain.ChatTipo;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.Instructor;
import mx.com.gm.domain.Mensaje;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.ChatRequest;
import mx.com.gm.dto.MensajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    JugadorEquipoDao jedao;
    @Autowired
    InstructorDao idao;
    @Autowired
    DeportistaDao ddao;
    @Autowired
    EquipoDao edao;
    @Autowired
    DeporteServiceImpl dservice;
    @Autowired
    ValidacionService vservice;
    @Autowired
    ChatDao chdao;
    @Autowired
    MensajeDao mdao;
    @Autowired
    OrganizacionDao odao;
    @Override
    public Chat crearChatIndividual(ChatRequest request) {
        Chat chatExistente = chdao.findChatByUniqueKeys(
        request.getInstructorId(),
        request.getDeportistaId(),
        request.getEquipoId(),
        request.getOrganizacionId(),
        request.getTipo(),
        request.getDeporteId()
    );
    if (chatExistente != null) {
        return chatExistente;
    }
        Instructor i = idao.findById(request.getInstructorId())
                .orElseThrow(()->new RuntimeException("Rutina no encontrada"));
        vservice.validarRelacionChat(request);
        Chat chat = new Chat();
        chat.setTipo(request.getTipo());
        chat.setDeporte(dservice.obtenerDeporteComun(request));
        switch(request.getTipo()) {
            case INSTRUCTOR_DEPORTISTA -> {
                Deportista d = ddao.findById(request.getDeportistaId())
                .orElseThrow(()->new RuntimeException("Rutina no encontrada"));
                chat.setInstructor(i);
                chat.setDeportista(d);
            }
            case EQUIPO -> {
                Equipo e = edao.findById(request.getEquipoId())
                .orElseThrow(()->new RuntimeException("Rutina no encontrada"));
                chat.setInstructor(i);
                chat.setEquipo(e);
                agregarParticipantesEquipo(chat);
            }
            case INSTRUCTOR_ORGANIZACION ->{
                Organizacion o = odao.findById(request.getOrganizacionId())
                        .orElseThrow(()->new RuntimeException("Rutina no encontrada"));
                chat.setInstructor(i);
                chat.setOrganizacion(o);
                
            }
        }
        
        return chdao.save(chat);
    }
    private void agregarParticipantesEquipo(Chat chat) {
        List<Deportista> jugadores = jedao.findDeportistasByEquipoId(chat.getEquipo().getId());
    
    jugadores.forEach(deportista -> {
        chat.getParticipantes().add(
            new ChatParticipante(chat, deportista)
        );
    });
    }
    @Override
    public List<Chat> obtenerChatsUsuario(Long userId, String tipoUsuario) {
        System.out.println(userId );
        System.out.println(tipoUsuario);
        return switch (tipoUsuario.toUpperCase()) {
            case "INSTRUCTOR" -> obtenerChatsInstructor(userId);
            case "DEPORTISTA" -> obtenerChatsDeportista(userId);
            case "ORGANIZACION" -> obtenerChatsOrganizacion(userId);
            default -> throw new IllegalArgumentException("Tipo de usuario no válido: " + tipoUsuario);
        };
    }

    private List<Chat> obtenerChatsInstructor(Long instructorId) {
        List<Chat> chats = new ArrayList<>();
        chats.addAll(chdao.findByInstructorIdAndTipoIn(
            instructorId, 
            List.of(ChatTipo.INSTRUCTOR_DEPORTISTA, ChatTipo.DEPORTISTA_INSTRUCTOR)
        ));
        chats.addAll(chdao.findByInstructorIdAndTipoIn(
            instructorId,
            List.of(ChatTipo.INSTRUCTOR_ORGANIZACION, ChatTipo.ORGANIZACION_INSTRUCTOR)
        ));
        chats.addAll(chdao.findChatsByInstructorDelEquipo(instructorId));
        
        return chats.stream().distinct().toList();
    }

    private List<Chat> obtenerChatsDeportista(Long deportistaId) {
         List<Chat> chats = new ArrayList<>();

    chats.addAll(chdao.findByDeportistaId(
        deportistaId 
    ));

    List<Long> equipoIds = chdao.findEquipoIdsByDeportistaId(deportistaId);

    if (!equipoIds.isEmpty()) {
        chats.addAll(chdao.findByEquipoIds(equipoIds));
    }
        System.out.println(chats.size());
    return chats.stream().distinct().toList();
    
    }

    private List<Chat> obtenerChatsOrganizacion(Long organizacionId) {
        return chdao.findByOrganizacionId(organizacionId);
    }

    @Override
    public Chat obtenerChatPorId(Long chatId) {
        return chdao.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado con ID: " + chatId));
    }
    @Override
    public Mensaje guardarMensajeDesdeDTO(MensajeDTO dto) {
        System.out.println(dto);
        Chat chat = chdao.findById(dto.getIdChat())
            .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        Mensaje mensaje = new Mensaje();
        mensaje.setChat(chat);
        mensaje.setContenido(dto.getContenido());
        mensaje.setRemitenteTipo(dto.getRemitenteTipo());
        mensaje.setRemitenteId(dto.getRemitenteId());
        mensaje.setFechaEnvio(dto.getFechaEnvio());
        mensaje.setLeido(dto.isLeido());

        return mdao.save(mensaje);
    }
    

}
