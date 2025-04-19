package mx.com.gm.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gm.dao.InstructorDao;
import mx.com.gm.dao.PosicionDao;
import mx.com.gm.dao.RutinaDao;
import mx.com.gm.domain.Instructor;
import mx.com.gm.domain.Posicion;
import mx.com.gm.domain.Rutina;
import mx.com.gm.dto.EjercicioRutinaDTO;
import mx.com.gm.dto.RecursoDTO;
import mx.com.gm.dto.RutinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutinaServiceImpl implements RutinaService{
    @Autowired
    private RutinaDao rdao;
    @Autowired
    private InstructorDao idao;

    @Autowired
    private PosicionDao pdao;

    @Override
    public List<Rutina> listbyInstId(Long id) {
        return rdao.findByInstructorId(id);
    }

    @Override
    public void delete(Long id) {
        rdao.deleteById(id);
    }

    @Override
    public Rutina add(RutinaDTO rutinaDTO) {
         Instructor instructor = idao.findById(rutinaDTO.getIdInstructor())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        Posicion posicion = pdao.findById(rutinaDTO.getIdPosicion())
                .orElseThrow(() -> new RuntimeException("Posición no encontrada"));

        Rutina rutina = new Rutina();
        rutina.setNombre(rutinaDTO.getNombre());
        rutina.setNivel_dificultad(rutinaDTO.getNivel_dificultad());
        rutina.setDia(rutinaDTO.getDia());
        rutina.setDescripcion(rutinaDTO.getDescripcion());
        rutina.setInstructor(instructor);
        rutina.setPosicion(posicion);
        rutina.setDuracion_esperada(rutinaDTO.getDuracion_esperada());
        rutina.setObjetivo(rutinaDTO.getObjetivo());

        return rdao.save(rutina);
    }

    @Override
    public Long getTotalRutinasByInstructorId(Long instructorId) {
        return rdao.countByInstructorId(instructorId);
    }

    @Override
    public List<Rutina> getTop3RutinasPopularesPorInstructor(Long instructorId) {
        return rdao.findTop3RutinasForInstructor(instructorId);
    }    

    @Override
    public List<RutinaDTO> rutinasEjerciciosRecursos(Long deportistaid) {
        List<Object[]> resultados = rdao.findRutinasCompletasByDeportistaIdNative(deportistaid);
        Map<Long, RutinaDTO> rutinasMap = new LinkedHashMap<>();
        Map<Long, EjercicioRutinaDTO> ejerciciosMap = new HashMap<>();
        for (Object[] fila : resultados) {
            Long rutinaId = ((Number)fila[0]).longValue();
            RutinaDTO rutinaDTO = rutinasMap.computeIfAbsent(rutinaId, id -> {
                RutinaDTO r = new RutinaDTO();
                r.setId(rutinaId);
                r.setNombre((String)fila[1]);
                r.setDia((String)fila[2]);
                r.setDescripcion((String)fila[3]);
                r.setNivel_dificultad((String)fila[4]);
                r.setObjetivo((String)fila[5]);
                r.setDuracion_esperada(((Number)fila[6]).longValue());
                r.setEjercicios(new ArrayList<>());
                return r;
            });
            Long ejercicioId = fila[7] != null ? ((Number)fila[7]).longValue() : null;
            if (ejercicioId != null && !ejerciciosMap.containsKey(ejercicioId)) {
                EjercicioRutinaDTO ejercicioDTO = new EjercicioRutinaDTO();
                ejercicioDTO.setId(ejercicioId);
                ejercicioDTO.setNombre((String)fila[8]);
                ejercicioDTO.setDescripcion((String)fila[9]);
                ejercicioDTO.setSeries((Integer)fila[10]);
                ejercicioDTO.setRepeticiones((String)fila[11]);
                ejercicioDTO.setDescanso((String)fila[12]);
                ejercicioDTO.setOrden((Integer)fila[13]);
                ejercicioDTO.setRecursos(new ArrayList<>());
                ejerciciosMap.put(ejercicioId, ejercicioDTO);
                rutinaDTO.getEjercicios().add(ejercicioDTO);
            }
            
            // Procesar recurso si existe
            Long recursoId = fila[14] != null ? ((Number)fila[14]).longValue() : null;
            if (recursoId != null && ejercicioId != null) {
                RecursoDTO recursoDTO = new RecursoDTO();
                recursoDTO.setId(recursoId);
                recursoDTO.setTipo((String)fila[15]);
                recursoDTO.setUrl((String)fila[16]);
                recursoDTO.setDescripcion((String)fila[17]);
                EjercicioRutinaDTO ejercicioDTO = ejerciciosMap.get(ejercicioId);
                if (ejercicioDTO != null) {
                    ejercicioDTO.getRecursos().add(recursoDTO);
                }
            }
        }
         return new ArrayList<>(rutinasMap.values());
    }


    @Override
    public List<RutinaDTO> rutinasByDiaAndDia(Long deportistaid) {
        String dia = obtenerDiaSemanaActual();
        List<Object[]> resultados = rdao.findRutinasCompletasByDeportistaIdAndDia(deportistaid, dia);
        Map<Long, RutinaDTO> rutinasMap = new LinkedHashMap<>();
        Map<Long, EjercicioRutinaDTO> ejerciciosMap = new HashMap<>();
        for (Object[] fila : resultados) {
            Long rutinaId = ((Number)fila[0]).longValue();
            RutinaDTO rutinaDTO = rutinasMap.computeIfAbsent(rutinaId, id -> {
                RutinaDTO r = new RutinaDTO();
                r.setId(rutinaId);
                r.setNombre((String)fila[1]);
                r.setDia((String)fila[2]);
                r.setDescripcion((String)fila[3]);
                r.setNivel_dificultad((String)fila[4]);
                r.setObjetivo((String)fila[5]);
                r.setDuracion_esperada(((Number)fila[6]).longValue());
                r.setEjercicios(new ArrayList<>());
                return r;
            });
            Long ejercicioId = fila[7] != null ? ((Number)fila[7]).longValue() : null;
            if (ejercicioId != null && !ejerciciosMap.containsKey(ejercicioId)) {
                EjercicioRutinaDTO ejercicioDTO = new EjercicioRutinaDTO();
                ejercicioDTO.setId(ejercicioId);
                ejercicioDTO.setNombre((String)fila[8]);
                ejercicioDTO.setDescripcion((String)fila[9]);
                ejercicioDTO.setSeries((Integer)fila[10]);
                ejercicioDTO.setRepeticiones((String)fila[11]);
                ejercicioDTO.setDescanso((String)fila[12]);
                ejercicioDTO.setOrden((Integer)fila[13]);
                ejercicioDTO.setRecursos(new ArrayList<>());
                ejerciciosMap.put(ejercicioId, ejercicioDTO);
                rutinaDTO.getEjercicios().add(ejercicioDTO);
            }
            
            // Procesar recurso si existe
            Long recursoId = fila[14] != null ? ((Number)fila[14]).longValue() : null;
            if (recursoId != null && ejercicioId != null) {
                RecursoDTO recursoDTO = new RecursoDTO();
                recursoDTO.setId(recursoId);
                recursoDTO.setTipo((String)fila[15]);
                recursoDTO.setUrl((String)fila[16]);
                recursoDTO.setDescripcion((String)fila[17]);
                EjercicioRutinaDTO ejercicioDTO = ejerciciosMap.get(ejercicioId);
                if (ejercicioDTO != null) {
                    ejercicioDTO.getRecursos().add(recursoDTO);
                }
            }
        }
         return new ArrayList<>(rutinasMap.values());
    }
    private String obtenerDiaSemanaActual() {
        Map<Integer, String> diasSemana = Map.of(
            1, "Lunes",
            2, "Martes",
            3, "Miércoles",
            4, "Jueves",
            5, "Viernes",
            6, "Sábado",
            7, "Domingo"
        );
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_WEEK);
        return diasSemana.get(dia == 1 ? 7 : dia - 1);
    }
}
