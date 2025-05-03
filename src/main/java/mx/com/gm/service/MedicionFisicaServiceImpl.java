package mx.com.gm.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import mx.com.gm.dao.MedicionFisicaDao;
import mx.com.gm.domain.MedicionFisica;
import mx.com.gm.dto.EvolucionFisicaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicionFisicaServiceImpl implements MedicionFisicaService{
    
    @Autowired 
    MedicionFisicaDao mfdao;

    @Override
    public List<EvolucionFisicaDTO> getEvolucionFisica(Long id, String rango) {
        LocalDate fechaFin = LocalDate.now();
        LocalDate fechaInicio = switch (rango) {
            case "1m" -> fechaFin.minusMonths(1);
            case "3m" -> fechaFin.minusMonths(3);
            case "6m" -> fechaFin.minusMonths(6);
            case "1a" -> fechaFin.minusYears(1);
            case "todo" -> LocalDate.of(2000, 1, 1);
            default -> throw new IllegalArgumentException("Rango no válido. Use: 1m, 3m, 6m, 1a, todo");
        };
        System.out.println(fechaFin);
        System.out.println(fechaInicio);
        List<MedicionFisica> mediciones = mfdao
                .findByDeportistaAndRangoFechas(id, fechaInicio, fechaFin);

        System.out.println(mediciones);
        return mediciones.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
     private EvolucionFisicaDTO convertirADTO(MedicionFisica medicion) {
        EvolucionFisicaDTO dto = new EvolucionFisicaDTO();
        dto.setFecha(medicion.getFecha());
        dto.setPeso(medicion.getPeso());
        dto.setImc(medicion.getImc());
        dto.setMasaMuscular(medicion.getMasaMuscular());
        dto.setPorcentajeGrasa(medicion.getPorcentajeGrasa());
        dto.setClasificacionIMC(clasificarIMC(medicion.getImc()));
        return dto;
    }
     private String clasificarIMC(Double imc) {
        if (imc == null) return "No calculado";
        if (imc < 18.5) return "Bajo peso";
        if (imc < 25) return "Normal";
        if (imc < 30) return "Sobrepeso";
        return "Obesidad";
    }
}
