package mx.com.gm.service;

import java.util.List;
import java.util.Optional;
import mx.com.gm.domain.MedicionFisica;
import mx.com.gm.dto.EvolucionFisicaDTO;
import mx.com.gm.dto.MedicionFisicaDTO;


public interface MedicionFisicaService {
    List<EvolucionFisicaDTO> getEvolucionFisica (Long id,String rango);
    public MedicionFisica add(MedicionFisicaDTO mfdto);
    List<MedicionFisica> list(Long id);
    Optional<MedicionFisica> getLastest(Long id);
    public void delete (Long id);
}
