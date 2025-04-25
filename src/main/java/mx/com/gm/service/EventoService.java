package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import mx.com.gm.domain.Evento;
import mx.com.gm.dto.EventoConEquiposDTO;
import mx.com.gm.dto.EventoDTO;
import org.springframework.web.multipart.MultipartFile;


public interface EventoService {
    public List<Evento> listByIdDeporte(Long id);
    public List<Evento> ProximosEventosByDeportistaId(Long deportista);
    public Evento crearEventoconFechas(EventoDTO edto,MultipartFile archivo) throws IOException ;
    public List<Evento> ProximosEventosByOrganizacionId(Long org);
    public void eliminarEvento(Long id);
    public Evento actualizarEvento(Long id, EventoDTO eventoActualizado);
    public List<EventoConEquiposDTO> getProximosEventosConEquipos(Long org);
    public Map<String,Object>getEstadisticasGenerales(Long org);
}
