package mx.com.gm.service;

import java.util.NoSuchElementException;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.EquipoDao;
import mx.com.gm.dao.InstructorDao;
import mx.com.gm.dao.OrganizacionDao;
import mx.com.gm.domain.Deporte;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.Instructor;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeporteServiceImpl {
    @Autowired 
    DeportistaDao ddao;
    @Autowired
    InstructorDao idao;
    @Autowired
    OrganizacionDao odao;
    @Autowired
    EquipoDao edao;
    public Deporte obtenerDeporteComun(ChatRequest request) {
        return switch (request.getTipo()) {
            case INSTRUCTOR_DEPORTISTA -> validarDeporteInstructorDeportista(
                request.getInstructorId(), 
                request.getDeportistaId()
            );
            
            case DEPORTISTA_INSTRUCTOR -> validarDeporteInstructorDeportista(
                request.getInstructorId(), 
                request.getDeportistaId()
            );
            
            case INSTRUCTOR_ORGANIZACION -> validarDeporteInstructorOrganizacion(
                request.getInstructorId(), 
                request.getOrganizacionId()
            );
            
            case ORGANIZACION_INSTRUCTOR -> validarDeporteInstructorOrganizacion(
                request.getInstructorId(), 
                request.getOrganizacionId()
            );
            
            case EQUIPO -> obtenerDeporteEquipo(request.getEquipoId());
            
            default -> throw new IllegalArgumentException("Tipo de chat no soportado");
        };
    }

    private Deporte validarDeporteInstructorDeportista(Long instructorId, Long deportistaId) {
        Instructor instructor = idao.findById(instructorId)
            .orElseThrow(() -> new NoSuchElementException("Instructor no encontrado"));
        
        Deportista deportista = ddao.findById(deportistaId)
            .orElseThrow(() -> new NoSuchElementException("Instructor no encontrado"));

        if (!instructor.getDeporte().getId().equals(deportista.getDeporte().getId())) {
            throw new IllegalArgumentException("Instructor y deportista deben pertenecer al mismo deporte");
        }
        
        return instructor.getDeporte();
    }

    private Deporte validarDeporteInstructorOrganizacion(Long instructorId, Long organizacionId) {
        Instructor instructor = idao.findById(instructorId)
            .orElseThrow(() -> new NoSuchElementException("Instructor no encontrado"));
        
        Organizacion organizacion = odao.findById(organizacionId)
            .orElseThrow(() -> new NoSuchElementException("Instructor no encontrado"));

        if (!instructor.getDeporte().getId().equals(organizacion.getDeporte().getId())) {
             throw new IllegalArgumentException("Instructor y deportista deben pertenecer al mismo deporte");
        }
        
        return instructor.getDeporte();
    }

    private Deporte obtenerDeporteEquipo(Long equipoId) {
        Equipo equipo = edao.findById(equipoId)
            .orElseThrow(() -> new NoSuchElementException("Instructor no encontrado"));
        
        return equipo.getDeporte();
    }
}
