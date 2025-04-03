package mx.com.gm.service;
import java.util.List;
import mx.com.gm.domain.RutinaJugador;
import mx.com.gm.dto.RutinaJugadorDTO;

public interface RutinaJugadorService {
    public List<RutinaJugador> listbyJugadorId(Long id);
    public RutinaJugador add(RutinaJugadorDTO rj);
}
