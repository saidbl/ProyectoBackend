package mx.com.gm.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.ObjetivoRendimientoDao;
import mx.com.gm.dao.RegistroRendimientoDao;
import mx.com.gm.dao.TipoMetricaDao;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.ObjetivoRendimiento;
import mx.com.gm.domain.RegistroRendimiento;
import mx.com.gm.domain.TipoMetrica;
import mx.com.gm.dto.ObjetivoRendimientoDTO;
import mx.com.gm.dto.ProgresoObjetivoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjetivoRendimientoServiceImpl implements ObjetivoRendimientoService{
    @Autowired
    ObjetivoRendimientoDao ordao;
    
    @Autowired
    DeportistaDao ddao;
    
    @Autowired
    TipoMetricaDao tmdao;
    
    @Autowired 
    RegistroRendimientoDao rrdao;

    @Override
    public List<ObjetivoRendimiento> getByDeportista(Long id) {
        return ordao.findByDeportistaId(id);
    }

    @Override
    public ObjetivoRendimiento add(ObjetivoRendimientoDTO ordto) {
        ObjetivoRendimiento or = new ObjetivoRendimiento();
        Deportista d = ddao.findById(ordto.getIddeportista())
                 .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        LocalDate f = LocalDate.now();
        TipoMetrica m = tmdao.findById(ordto.getIdmetrica())
                  .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        or.setCompletado(false);
        or.setDeportista(d);
        or.setFechaEstablecido(f);
        or.setFechaObjetivo(ordto.getFechaObjetivo());
        or.setMetrica(m);
        or.setPrioridad(ordto.getPrioridad());
        or.setValorObjetivo(ordto.getValorObjetivo());
        return ordao.save(or);
    }

    @Override
    public List<ProgresoObjetivoDTO> obtenerProgresoObjetivos(Long deportistaId) {
        List<ObjetivoRendimiento> objetivos = ordao.findByDeportistaId(deportistaId);
        List<ProgresoObjetivoDTO> resultados = new ArrayList<>();

        for (ObjetivoRendimiento objetivo : objetivos) {
            ProgresoObjetivoDTO dto = new ProgresoObjetivoDTO();
            dto.setId(objetivo.getId());
            dto.setNombreObjetivo(objetivo.getMetrica().getNombre());
            dto.setUnidad(objetivo.getMetrica().getUnidad());
            dto.setValorObjetivo(objetivo.getValorObjetivo());
            dto.setFechaObjetivo(objetivo.getFechaObjetivo());
            dto.setCompletado(objetivo.isCompletado());
            System.out.println(dto);

            Optional<RegistroRendimiento> ultimoRegistro = rrdao.findUltimoRegistro(
                    deportistaId, objetivo.getMetrica().getId());

            if (ultimoRegistro.isPresent()) {
                double valorActual = ultimoRegistro.get().getValor();
                dto.setValorActual(valorActual);
                double porcentaje = (valorActual / objetivo.getValorObjetivo()) * 100;
                dto.setPorcentajeCompletado((int) Math.min(100, Math.round(porcentaje)));
            } else {
                dto.setValorActual(0.0);
                dto.setPorcentajeCompletado(0);
            }

            resultados.add(dto);
        }

        return resultados;
    }

    @Override
    public ObjetivoRendimiento completado(Long id) {
        ObjetivoRendimiento obj = ordao.findById(id)
                 .orElseThrow(() -> new RuntimeException("Objetivo no encontrada"));
        obj.setCompletado(true);
        return ordao.save(obj);
    }

    @Override
    public void delete(Long id) throws IOException {
        ordao.deleteById(id);
    }
    }
    
