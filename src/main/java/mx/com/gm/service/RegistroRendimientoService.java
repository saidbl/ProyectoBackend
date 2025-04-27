package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.RegistroRendimiento;
import mx.com.gm.dto.RegistroRendimientoDTO;

public interface RegistroRendimientoService {
    public List<RegistroRendimiento> getRendimientoDeportista(Long deportistaid, String periodo);
    public RegistroRendimiento add (RegistroRendimientoDTO rdto);
}
