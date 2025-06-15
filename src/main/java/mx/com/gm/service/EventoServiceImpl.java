package mx.com.gm.service;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mx.com.gm.dao.DeporteDao;
import mx.com.gm.dao.EquipoDao;
import mx.com.gm.dao.EventoDao;
import mx.com.gm.dao.EventoFechaDao;
import mx.com.gm.dao.OrganizacionDao;
import mx.com.gm.domain.Deporte;
import mx.com.gm.domain.Evento;
import mx.com.gm.domain.EventoFecha;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.EventoConEquiposDTO;
import mx.com.gm.dto.EventoDTO;
import mx.com.gm.dto.TipoRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class EventoServiceImpl implements EventoService{
    
    @Autowired
    EventoDao edao;
    
    @Autowired 
    DeporteDao ddao;
    
    @Autowired 
    OrganizacionDao odao;
    
    @Autowired 
    EventoFechaDao efdao;
    
    @Autowired 
    FileStorageService fsservice;
    
    @Autowired
    EquipoDao eqdao;
    

    @Override
    public List<Evento> listByIdDeporte(Long id) {
        return edao.findByDeporteId(id);
    }

    @Override
    public List<Evento> ProximosEventosByDeportistaId(Long deportista) {
        return edao.findProximosEventosByDeportistaId(deportista);
    }

    @Override
    public Evento crearEventoconFechas(EventoDTO edto, MultipartFile archivo)  throws IOException {
        TipoRecurso tipo = TipoRecurso.fromContentType(archivo.getContentType());
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de archivo no soportado: " + archivo.getContentType());
        }
        String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
        String rutaArchivo = fsservice.guardarArchivo(
            archivo.getInputStream(), 
            tipo.getNombreCarpeta(), 
            nombreArchivo
        );
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
        evento.setImagen(rutaArchivo);
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
    if (frecuencia.equals("SEMANAL") && (diasSemana == null || diasSemana.isEmpty())) {
        String diaInicial = convertirDiaLocalDateATexto(inicio.getDayOfWeek());
        diasSemana = List.of(diaInicial);
    }
    LocalDate fechaActual = inicio;
    while (!fechaActual.isAfter(fin)) {
        if (cumpleCondiciones(fechaActual, frecuencia, diasSemana, excluirFines)) {
            fechas.add(fechaActual);
        }
        if (frecuencia.equals("SEMANAL")) {
            fechaActual = fechaActual.plusDays(1);
        } else {
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
    if (excluirFines) {
        DayOfWeek dayOfWeek = fecha.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return false;
        }
    }
    if (frecuencia.equals("SEMANAL")) {
        String diaActual = convertirDiaLocalDateATexto(fecha.getDayOfWeek());
        return diasSemana.contains(diaActual);
    }
    return true;
}
     private String convertirDiaLocalDateATexto(DayOfWeek dayOfWeek) {
    return switch (dayOfWeek) {
        case MONDAY -> "L";
        case TUESDAY -> "M"; 
        case WEDNESDAY -> "X";
        case THURSDAY -> "J";
        case FRIDAY -> "V"; 
        case SATURDAY -> "S";
        case SUNDAY -> "D";
    };
}

    @Override
    public List<Evento> ProximosEventosByOrganizacionId(Long org) {
         return edao.findEventosFuturosByOrganizacion(org);
    }

    @Override
    public void eliminarEvento(Long id) {
        edao.deleteById(id);
    }

    @Transactional
    @Override
    public Evento actualizarEvento(Long id, EventoDTO eventoActualizado) {
         Evento eventoExistente = edao.findById(id)
            .orElseThrow(() -> new RuntimeException("Deporte no encontrada"));
        eventoExistente.setNombre(eventoActualizado.getNombre());
        eventoExistente.setFecha(eventoActualizado.getFecha());
        eventoExistente.setFechaFin(eventoActualizado.getFechaFin());
        eventoExistente.setUbicacion(eventoActualizado.getUbicacion());
        eventoExistente.setHoraInicio(eventoActualizado.getHoraInicio());
        eventoExistente.setHoraFin(eventoActualizado.getHoraFin());
        eventoExistente.setDescripcion(eventoActualizado.getDescripcion());
        eventoExistente.setNumMaxEquipos(eventoActualizado.getNumMaxEquipos());
        eventoExistente.setEstado(eventoActualizado.getEstado());
        eventoExistente.setRecurrente(eventoActualizado.getRecurrente());
        eventoExistente.setFrecuencia(eventoActualizado.getFrecuencia());
        if (eventoActualizado.getFrecuencia().equals("MENSUAL")||eventoActualizado.getFrecuencia().equals("ANUAL")){
            eventoExistente.setDiasSemana(null);
        }   else{
            eventoExistente.setDiasSemana(eventoActualizado.getDiasSemana().toString());
        }
        eventoExistente.setExcluirFines(eventoActualizado.getExcluirFines());
        if (eventoActualizado.getRecurrente()) {
            efdao.deleteByEventoId(id);
            
            generarFechasRecurrentes(eventoExistente, eventoActualizado);
        } else {
            efdao.deleteByEventoId(id);
            generarFechaUnica(eventoExistente, eventoActualizado);
        }
        return edao.save(eventoExistente);
    }
    @Override
     public List<EventoConEquiposDTO> getProximosEventosConEquipos(Long org) {
        List<Evento> eventos = edao.findEventosFuturosByOrganizacion(org);
        return eventos.stream()
            .map(evento -> new EventoConEquiposDTO(
                evento,
                eqdao.findEquiposByEventoIdNative(evento.getId())
            ))
            .toList();
    }

    @Override
    public Map<String, Object> getEstadisticasGenerales(Long org) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("eventosPorMes", edao.countEventosPorMes(org));
        stats.put("participacionEventos", edao.getParticipacionEventos(org));
        return stats;
    }

}
