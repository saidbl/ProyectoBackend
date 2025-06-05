
package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.TipoMetrica;
import mx.com.gm.dto.TipoMetricaDTO;

public interface TipoMetricaService {
    public TipoMetrica add (TipoMetricaDTO tmdto);
    public List<TipoMetrica> list (Long id);
    public void delete (Long id) throws IOException;
}
