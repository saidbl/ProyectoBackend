package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.Evento;
import mx.com.gm.dto.EventoDTO;
import mx.com.gm.dto.EventoDeportistaDTO;
import org.springframework.web.multipart.MultipartFile;


public interface EventoService {
    public List<Evento> listByIdDeporte(Long id);
    public List<Evento> ProximosEventosByDeportistaId(Long deportista);
    public Evento crearEventoconFechas(EventoDTO edto,MultipartFile archivo) throws IOException ;
}
