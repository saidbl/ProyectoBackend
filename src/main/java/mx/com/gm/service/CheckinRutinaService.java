package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.CheckinRutina;
import mx.com.gm.dto.CheckinRutinaDTO;
import mx.com.gm.dto.CumplimientoRutinasDTO;
import mx.com.gm.dto.ResumenCumplimientoDTO;


public interface CheckinRutinaService {
    public CheckinRutina add (CheckinRutinaDTO crdto);
    public List<CheckinRutina> listByDeportistaId(Long id);
    public CumplimientoRutinasDTO getCumplimiento(Long id, String rango);
    public ResumenCumplimientoDTO obtenerCumplimientoRutinas(Long id);

}
