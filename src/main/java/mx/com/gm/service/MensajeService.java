package mx.com.gm.service;

import mx.com.gm.domain.Mensaje;
import mx.com.gm.domain.MensajeRequest;
import mx.com.gm.dto.MensajeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MensajeService {
    public Page<MensajeDTO> obtenerMensajesPaginados(Long chatId, Pageable pageable) ;
    public void marcarMensajesComoLeidos(Long chatId, Long usuarioId);
    public Mensaje enviarMensaje(MensajeRequest request);
}
