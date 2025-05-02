package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.ObjetivoRendimiento;
import mx.com.gm.dto.ObjetivoRendimientoDTO;


public interface ObjetivoRendimientoService {
    List<ObjetivoRendimiento> getByDeportista(Long id);
    public ObjetivoRendimiento add(ObjetivoRendimientoDTO ordto);
}
