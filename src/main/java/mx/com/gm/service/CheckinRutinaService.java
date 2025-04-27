package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.CheckinRutina;
import mx.com.gm.dto.CheckinRutinaDTO;


public interface CheckinRutinaService {
    public CheckinRutina add (CheckinRutinaDTO crdto);
    public List<CheckinRutina> listByDeportistaId(Long id);

}
