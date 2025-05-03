package mx.com.gm.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.com.gm.dao.CheckinRutinaDao;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.RutinaDao;
import mx.com.gm.domain.CheckinRutina;
import mx.com.gm.domain.CheckinRutina.EstadoCheckin;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Rutina;
import mx.com.gm.dto.CheckinRutinaDTO;
import mx.com.gm.dto.CumplimientoRutinasDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckinRutinaServiceImpl implements CheckinRutinaService{
    @Autowired 
    CheckinRutinaDao crdao;
    
    @Autowired 
    RutinaDao rdao ;
    
    @Autowired 
    DeportistaDao ddao ;

    @Override
    public CheckinRutina add(CheckinRutinaDTO crdto) {
        CheckinRutina cr = new CheckinRutina();
        System.out.println(crdto);
        Rutina r = rdao.findById(crdto.getIdrutina())
                .orElseThrow(()->new RuntimeException("Rutina no encontrada"));
        Deportista d = ddao.findById(crdto.getIdjugador())
                .orElseThrow(()->new RuntimeException("Deportista no encontrado"));
        LocalTime hora = LocalTime.now();
        LocalDate fecha = LocalDate.now();
        cr.setRutina(r);
        cr.setJugador(d);
        cr.setHora(hora);
        cr.setFecha(fecha);
        cr.setEstado(CheckinRutina.EstadoCheckin.COMPLETADA);
        cr.setComentarios(crdto.getComentarios());
        return crdao.save(cr);
    }

    @Override
    public List<CheckinRutina> listByDeportistaId(Long id) {
        return crdao.findByDeportistaIdAndEstado(id);
    }

    @Override
    public CumplimientoRutinasDTO getCumplimiento(Long id, String rango) {
        LocalDate fechaInicio = switch (rango.toLowerCase()) {
            case "1m" -> LocalDate.now().minusMonths(1);
            case "3m" -> LocalDate.now().minusMonths(3);
            case "6m" -> LocalDate.now().minusMonths(6);
            default -> throw new IllegalArgumentException("Rango no válido. Use: 1m, 3m, 6m");
        };
        CumplimientoRutinasDTO dto = new CumplimientoRutinasDTO();
        dto.setTotalRutinas(crdao.countByJugadorIdAndFechaAfter(id, fechaInicio));
        dto.setCompletadas(crdao.countByJugadorIdAndEstadoAndFechaAfter(
                id, EstadoCheckin.COMPLETADA, fechaInicio));
        dto.setIncompletas(crdao.countByJugadorIdAndEstadoAndFechaAfter(
                id, EstadoCheckin.INCOMPLETA, fechaInicio));

        if (dto.getTotalRutinas() > 0) {
            dto.setPorcentajeCompletadas(
                    (int) Math.round((dto.getCompletadas() * 100.0) / dto.getTotalRutinas()));
        }

        List<Object[]> resultadosDia = crdao.countByJugadorIdGroupByDiaSemana(
                id, fechaInicio);
        Map<String, Long> rutinasPorDia = new HashMap<>();
        resultadosDia.forEach(arr -> rutinasPorDia.put((String) arr[0], (Long) arr[1]));
        dto.setRutinasPorDia(rutinasPorDia);
        return dto;
    }
    }

