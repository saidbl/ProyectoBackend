package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import mx.com.gm.dao.DeporteDao;
import mx.com.gm.dao.EquipoDao;
import mx.com.gm.dao.InstructorDao;
import mx.com.gm.domain.Deporte;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.Instructor;
import mx.com.gm.dto.EquipoDTO;
import mx.com.gm.dto.TipoRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EquipoServiceImpl implements EquipoService{

    @Autowired 
    private EquipoDao edao;
    
    @Autowired
    private InstructorDao idao;

    @Autowired
    private DeporteDao pdao;
    
    @Autowired 
    FileStorageService fsservice;
    
    @Override
    public List<Equipo> listByIdInstructor(Long id) {
        return edao.findByInstructorId(id);
    }

    @Override
    public void delete(Long id) {
        edao.deleteById(id);
    }

    @Override
    public Equipo add(EquipoDTO e, MultipartFile archivo)throws IOException  {
        TipoRecurso tipo = TipoRecurso.fromContentType(archivo.getContentType());
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de archivo no soportado: " + archivo.getContentType());
        }
        String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
        String rutaArchivo = fsservice.guardarArchivo(
            archivo.getInputStream(), 
            tipo.getNombreCarpeta(), 
            nombreArchivo
        );
        Instructor instructor = idao.findById(e.getIdinstructor())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        Deporte deporte = pdao.findById(e.getIddeporte())
                .orElseThrow(() -> new RuntimeException("Deporte no encontrada"));

        Equipo equipo = new Equipo();
        equipo.setNombre(e.getNombre());
        equipo.setInstructor(instructor);
        equipo.setDeporte(deporte);
        equipo.setMaxJugadores(e.getMaxJugadores());
        equipo.setFechaCreacion(e.getFechaCreacion());
        equipo.setEstado(e.getEstado());
        equipo.setCategoria(e.getCategoria());
        equipo.setJugadoresAsociados(e.getJugadoresAsociados());
        equipo.setImg(rutaArchivo);
        return edao.save(equipo);
    }

    @Override
    public List<Equipo> listbyidjugador(Long id) {
        return edao.findEquiposActivosByJugadorIdNative(id);
    }
    
}
