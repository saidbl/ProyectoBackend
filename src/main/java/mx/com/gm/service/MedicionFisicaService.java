package mx.com.gm.service;

import java.util.List;
import mx.com.gm.dto.EvolucionFisicaDTO;


public interface MedicionFisicaService {
    List<EvolucionFisicaDTO> getEvolucionFisica (Long id,String rango);
}
