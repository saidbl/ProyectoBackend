package mx.com.gm.service;

import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.EquipoDao;
import mx.com.gm.dao.InstructorDao;
import mx.com.gm.dao.OrganizacionDao;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.Instructor;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidacionService {

    @Autowired
    InstructorDao idao;
    @Autowired
    DeportistaDao ddao;
    @Autowired
    EquipoDao edao;
    @Autowired 
    OrganizacionDao odao;
     public void validarRelacionChat(ChatRequest request) {
        switch(request.getTipo()) {
            case INSTRUCTOR_DEPORTISTA, DEPORTISTA_INSTRUCTOR -> validarInstructorDeportista(request.getInstructorId(), request.getDeportistaId());
            case INSTRUCTOR_ORGANIZACION, ORGANIZACION_INSTRUCTOR -> validarInstructorOrganizacion(request.getInstructorId(), request.getOrganizacionId());
                
            case EQUIPO -> validarInstructorEquipo(request.getInstructorId(), request.getEquipoId());
            default -> throw new IllegalArgumentException("Tipo de chat no válido: " + request.getTipo());
        }
    }

    private void validarInstructorDeportista(Long instructorId, Long deportistaId) {
        Instructor instructor = idao.findById(instructorId)
            .orElseThrow(()->new RuntimeException("Rutina no encontrada"));
        
        Deportista deportista = ddao.findById(deportistaId)
            .orElseThrow(()->new RuntimeException("Rutina no encontrada"));

        if (!instructor.getDeporte().getId().equals(deportista.getDeporte().getId())) {
             throw new IllegalArgumentException("Instructor y deportista deben pertenecer al mismo deporte");
        }
        if (!deportista.getInstructor().getId().equals(instructorId)) {
             throw new IllegalArgumentException("Instructor y deportista deben pertenecer al mismo deporte");
        }
    }
    private void validarInstructorOrganizacion(Long instructorId, Long organizacionId) {
        Instructor instructor = idao.findById(instructorId)
           .orElseThrow(()->new RuntimeException("Rutina no encontrada"));
        
        Organizacion organizacion = odao.findById(organizacionId)
           .orElseThrow(()->new RuntimeException("Rutina no encontrada"));

        if (!instructor.getDeporte().getId().equals(organizacion.getDeporte().getId())) {
            throw new IllegalArgumentException("Instructor y deportista deben pertenecer al mismo deporte");
        }
    }

    private void validarInstructorEquipo(Long instructorId, Long equipoId) {
        Equipo equipo = edao.findById(equipoId)
            .orElseThrow(()->new RuntimeException("Rutina no encontrada"));

        if (!equipo.getInstructor().getId().equals(instructorId)) {
                         throw new IllegalArgumentException("Instructor y deportista deben pertenecer al mismo deporte");
        }
    }
}
