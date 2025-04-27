package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.ObjetivoRendimiento;


public interface ObjetivoRendimientoService {
    List<ObjetivoRendimiento> getByDeportista(Long id);
}
