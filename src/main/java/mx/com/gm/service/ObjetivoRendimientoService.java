package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.ObjetivoRendimiento;
import mx.com.gm.dto.ObjetivoRendimientoDTO;
import mx.com.gm.dto.ProgresoObjetivoDTO;


public interface ObjetivoRendimientoService {
    List<ObjetivoRendimiento> getByDeportista(Long id);
    public ObjetivoRendimiento add(ObjetivoRendimientoDTO ordto);
    public List<ProgresoObjetivoDTO> obtenerProgresoObjetivos(Long deportistaId);
    public ObjetivoRendimiento completado (Long id);
    public void delete (Long id)throws IOException;
}
