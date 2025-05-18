package mx.com.gm.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mx.com.gm.dao.CheckinRutinaDao;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.RutinaDao;
import mx.com.gm.domain.CheckinRutina;
import mx.com.gm.domain.CheckinRutina.EstadoCheckin;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Rutina;
import mx.com.gm.dto.AtletaPendienteDTO;
import mx.com.gm.dto.CheckinRutinaDTO;
import mx.com.gm.dto.CumplimientoRutinasDTO;
import mx.com.gm.dto.DiaSemanaDTO;
import mx.com.gm.dto.ResumenCumplimientoDTO;
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

    @Override
    public ResumenCumplimientoDTO obtenerCumplimientoRutinas(Long id) {
        LocalDate fin = LocalDate.now();
    LocalDate inicio = fin.minusDays(30);
    Map<EstadoCheckin, Long> conteoEstados = crdao
        .getResumenCompliance(inicio, fin, id)
        .stream()
        .collect(Collectors.toMap(
            arr -> (EstadoCheckin) arr[0],
            arr -> (Long) arr[1]
        ));
    long total = conteoEstados.values().stream().mapToLong(Long::longValue).sum();
    double completadas = conteoEstados.getOrDefault(EstadoCheckin.COMPLETADA, 0L) * 100.0 / total;
    double incompletas = 100.0 - completadas;
    List<DiaSemanaDTO> dias = crdao.getCompliancePorDia(inicio, fin, id)
        .stream()
        .map(arr -> new DiaSemanaDTO(
            obtenerNombreDia((Integer) arr[0]),
            (Double) arr[1]
        ))
        .sorted(Comparator.comparing(DiaSemanaDTO::getPorcentajeCumplimiento).reversed())
        .collect(Collectors.toList());
    List<AtletaPendienteDTO> pendientes = crdao.getTopPendientes(inicio, fin, id)
        .stream()
        .map(arr -> new AtletaPendienteDTO(
            ((Deportista) arr[0]).getNombre(),
            (Long) arr[1]
        ))
        .collect(Collectors.toList());

    return new ResumenCumplimientoDTO(
        completadas,
        incompletas,
        dias,
        pendientes
    );
}

private String obtenerNombreDia(int dayOfWeek) {
    return switch (dayOfWeek) {
        case 1 -> "Domingo";
        case 2 -> "Lunes";
        case 3 -> "Martes";
        case 4 -> "Miércoles";
        case 5 -> "Jueves";
        case 6 -> "Viernes";
        case 7 -> "Sábado";
        default -> "";
    };
    }
    }

