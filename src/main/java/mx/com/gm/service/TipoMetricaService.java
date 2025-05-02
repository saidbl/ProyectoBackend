
package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.TipoMetrica;
import mx.com.gm.dto.TipoMetricaDTO;

public interface TipoMetricaService {
    public TipoMetrica add (TipoMetricaDTO tmdto);
    public List<TipoMetrica> list (Long id);
}
