package mx.com.gm.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import mx.com.gm.dao.DeporteDao;
import mx.com.gm.dao.EventoDao;
import mx.com.gm.dao.EventoFechaDao;
import mx.com.gm.dao.OrganizacionDao;
import mx.com.gm.domain.Deporte;
import mx.com.gm.domain.Evento;
import mx.com.gm.domain.EventoFecha;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.EventoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoServiceImpl implements EventoService{
    
    @Autowired
    EventoDao edao;
    
    @Autowired 
    DeporteDao ddao;
    
    @Autowired 
    OrganizacionDao odao;
    
    @Autowired 
    EventoFechaDao efdao;
    

    @Override
    public List<Evento> listByIdDeporte(Long id) {
        return edao.findByDeporteId(id);
    }

    @Override
    public List<Evento> ProximosEventosByDeportistaId(Long deportista) {
        return edao.findProximosEventosByDeportistaId(deportista);
    }

    @Override
    public Evento crearEventoconFechas(EventoDTO edto) {
       Evento evento = new Evento();
       Deporte d = ddao.findById(edto.getIdDeporte())
               .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
       Organizacion o = odao.findById(edto.getIdOrganizacion())
               .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        evento.setContactoOrganizador(edto.getContactoOrganizador());
        evento.setDeporte(d);
        evento.setDescripcion(edto.getDescripcion());
        evento.setDiasSemana(edto.getDiasSemana().toString());
        evento.setEquiposInscritos(edto.getEquiposInscritos());
        evento.setEstado(edto.getEstado());
        evento.setExcluirFines(edto.getExcluirFines());
        evento.setFecha(edto.getFecha());
        evento.setFechaFin(edto.getFechaFin());
        evento.setFrecuencia(edto.getFrecuencia());
        evento.setHoraFin(edto.getHoraFin());
        evento.setHoraInicio(edto.getHoraInicio());
        evento.setNombre(edto.getNombre());
        evento.setNumMaxEquipos(edto.getNumMaxEquipos());
        evento.setOrganizacion(o);
        evento.setRecurrente(edto.getRecurrente());
        evento.setUbicacion(edto.getUbicacion());
        if (edto.getRecurrente()) {
            generarFechasRecurrentes(evento, edto);
        } else {
            generarFechaUnica(evento, edto);
        }
        
        return edao.save(evento);
    }
    private void generarFechaUnica(Evento evento, EventoDTO eventoDTO) {
        EventoFecha eventoFecha = new EventoFecha();
        eventoFecha.setFecha(eventoDTO.getFecha());
        eventoFecha.setHoraInicio(eventoDTO.getHoraInicio());
        eventoFecha.setHoraFin(eventoDTO.getHoraFin());
        eventoFecha.setEvento(evento);
        eventoFecha.setEstado("ACTIVO");
        efdao.save(eventoFecha);
    }
      private void generarFechasRecurrentes(Evento evento, EventoDTO eventoDTO) {
        List<LocalDate> fechas = calcularFechasRecurrentes(
            eventoDTO.getFecha(),
            eventoDTO.getFechaFin(),
            eventoDTO.getFrecuencia(),
            eventoDTO.getDiasSemana(),
            eventoDTO.getExcluirFines()
        );

        for (LocalDate fecha : fechas) {
            EventoFecha eventoFecha = new EventoFecha();
            eventoFecha.setFecha(fecha);
            eventoFecha.setHoraInicio(eventoDTO.getHoraInicio());
            eventoFecha.setHoraFin(eventoDTO.getHoraFin());
            eventoFecha.setEvento(evento);
            eventoFecha.setEstado("ACTIVO");
            efdao.save(eventoFecha);
        }
    }
    
     private List<LocalDate> calcularFechasRecurrentes(LocalDate inicio, LocalDate fin, String frecuencia, 
                                               List<String> diasSemana, boolean excluirFines) {
        List<LocalDate> fechas = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date date = Date.from(inicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        calendar.setTime(date);
        if (!cumpleFrecuencia(calendar, frecuencia, diasSemana, excluirFines)) {
            avanzarCalendario(calendar, frecuencia);
        }

        Date datefin = Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant());
        while (!calendar.getTime().after(datefin)) {
            if (cumpleFrecuencia(calendar, frecuencia, diasSemana, excluirFines)) {
                Date fecha = calendar.getTime();
                fechas.add( fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            avanzarCalendario(calendar, frecuencia);
        }
        return fechas;
    }
     
     private void avanzarCalendario(Calendar calendar, String frecuencia) {
        switch (frecuencia) {
            case "DIARIO" -> calendar.add(Calendar.DAY_OF_MONTH, 1);
            case "SEMANAL" -> calendar.add(Calendar.WEEK_OF_YEAR, 1);
            case "MENSUAL" -> calendar.add(Calendar.MONTH, 1);
            case "ANUAL" -> calendar.add(Calendar.YEAR, 1);
            default -> calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
     private boolean cumpleFrecuencia(Calendar calendar, String frecuencia, 
                                   List<String> diasSemana, boolean excluirFines) {
        if (excluirFines) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                return false;
            }
        }
        if (frecuencia.equals("SEMANAL") && diasSemana != null && !diasSemana.isEmpty()) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String diaActual = convertirDiaCalendarioATexto(dayOfWeek);
            return diasSemana.contains(diaActual);
        }
        return true;
    }
     
     private String convertirDiaCalendarioATexto(int dayOfWeek) {
        return switch (dayOfWeek) {
            case Calendar.MONDAY -> "L";
            case Calendar.TUESDAY -> "M";
            case Calendar.WEDNESDAY -> "X";
            case Calendar.THURSDAY -> "J";
            case Calendar.FRIDAY -> "V";
            case Calendar.SATURDAY -> "S";
            case Calendar.SUNDAY -> "D";
            default -> "";
        };
    }
    
}
