package mx.com.gm.service;

import java.time.DayOfWeek;
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
        Evento e =  edao.save(evento);
        if (edto.getRecurrente()) {
            System.out.println("Entro al if");
            generarFechasRecurrentes(evento, edto);
        } else {
            System.out.println("Entro al else");
            generarFechaUnica(evento, edto);
        }
        System.out.println("No entro");
        return e;
    }
    private void generarFechaUnica(Evento evento, EventoDTO eventoDTO) {
        System.out.println("Dentro de funcion");
        EventoFecha eventoFecha = new EventoFecha();
        eventoFecha.setFecha(eventoDTO.getFecha());
        eventoFecha.setHoraInicio(eventoDTO.getHoraInicio());
        eventoFecha.setHoraFin(eventoDTO.getHoraFin());
        eventoFecha.setEvento(evento);
        eventoFecha.setEstado("ACTIVO");
        System.out.println(eventoFecha);
        efdao.save(eventoFecha);
    }
      private void generarFechasRecurrentes(Evento evento, EventoDTO eventoDTO) {
        System.out.println("Dentro de funcion");
          System.out.println(eventoDTO.getDiasSemana());
        List<LocalDate> fechas = calcularFechasRecurrentes(
            eventoDTO.getFecha(),
            eventoDTO.getFechaFin(),
            eventoDTO.getFrecuencia(),
            eventoDTO.getDiasSemana(),
            eventoDTO.getExcluirFines()
        );
        System.out.println("Antes de Funcion");
          System.out.println(fechas);
        for (LocalDate fecha : fechas) {
            EventoFecha eventoFecha = new EventoFecha();
            eventoFecha.setFecha(fecha);
            eventoFecha.setHoraInicio(eventoDTO.getHoraInicio());
            eventoFecha.setHoraFin(eventoDTO.getHoraFin());
            eventoFecha.setEvento(evento);
            eventoFecha.setEstado("ACTIVO");
            System.out.println(eventoFecha.getId());
            System.out.println(eventoFecha);
            efdao.save(eventoFecha);
        }
    }
    
     private List<LocalDate> calcularFechasRecurrentes(LocalDate inicio, LocalDate fin, String frecuencia, 
                                               List<String> diasSemana, boolean excluirFines) {
        List<LocalDate> fechas = new ArrayList<>();
    
    // Si es semanal y no hay días especificados, usar el día de la fecha inicial
    if (frecuencia.equals("SEMANAL") && (diasSemana == null || diasSemana.isEmpty())) {
        String diaInicial = convertirDiaLocalDateATexto(inicio.getDayOfWeek());
        diasSemana = List.of(diaInicial);
    }
    
    LocalDate fechaActual = inicio;
    while (!fechaActual.isAfter(fin)) {
        if (cumpleCondiciones(fechaActual, frecuencia, diasSemana, excluirFines)) {
            fechas.add(fechaActual);
        }
        
        // Avance diferente para frecuencia semanal
        if (frecuencia.equals("SEMANAL")) {
            // Avanzamos día por día para encontrar todos los días especificados
            fechaActual = fechaActual.plusDays(1);
        } else {
            // Para otras frecuencias
            fechaActual = avanzarFechaSegunFrecuencia(fechaActual, frecuencia);
        }
    }
    
    return fechas;
    }
     
     private LocalDate avanzarFechaSegunFrecuencia(LocalDate fecha, String frecuencia) {
    return switch (frecuencia) {
        case "DIARIO" -> fecha.plusDays(1);
        case "MENSUAL" -> fecha.plusMonths(1);
        case "ANUAL" -> fecha.plusYears(1);
        default -> fecha.plusDays(1);
    };
}
     
    private boolean cumpleCondiciones(LocalDate fecha, String frecuencia, 
     List<String> diasSemana, boolean excluirFines) {
    
    // 1. Verificar exclusión de fines de semana
    if (excluirFines) {
        DayOfWeek dayOfWeek = fecha.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return false;
        }
    }
    
    // 2. Verificar días específicos para frecuencia semanal
    if (frecuencia.equals("SEMANAL")) {
        String diaActual = convertirDiaLocalDateATexto(fecha.getDayOfWeek());
        return diasSemana.contains(diaActual);
    }
    
    // 3. Para otras frecuencias
    return true;
}
     private String convertirDiaLocalDateATexto(DayOfWeek dayOfWeek) {
    return switch (dayOfWeek) {
        case MONDAY -> "L";
        case TUESDAY -> "M"; // Martes
        case WEDNESDAY -> "X";
        case THURSDAY -> "J";
        case FRIDAY -> "V"; // Viernes
        case SATURDAY -> "S";
        case SUNDAY -> "D";
    };
}
    
}
