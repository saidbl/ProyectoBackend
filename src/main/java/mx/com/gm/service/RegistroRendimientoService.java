package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.RegistroRendimiento;
import mx.com.gm.dto.RegistroRendimientoDTO;

public interface RegistroRendimientoService {
    public List<RegistroRendimiento> getRendimientoDeportista(Long deportistaid);
    public RegistroRendimiento add (RegistroRendimientoDTO rdto);
    public void delete (Long id)throws IOException;
}
