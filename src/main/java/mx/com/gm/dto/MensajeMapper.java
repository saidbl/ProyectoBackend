
package mx.com.gm.dto;

import java.util.List;
import mx.com.gm.domain.Mensaje;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MensajeMapper {
    MensajeDTO toDto(Mensaje mensaje);
    List<MensajeDTO> toDtoList(List<Mensaje> mensajes);
}
