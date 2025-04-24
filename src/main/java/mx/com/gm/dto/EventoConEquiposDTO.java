package mx.com.gm.dto;

import java.util.List;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.Evento;
public record EventoConEquiposDTO(
    Evento evento,
    List<Equipo> equipos
) {}

