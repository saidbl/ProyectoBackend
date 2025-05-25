/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.com.gm.dto;

import java.time.LocalDateTime;
import lombok.Data;
import mx.com.gm.domain.RemitenteTipo;

@Data
public class MensajeDTO {
    private Long id;
    private Long idChat;
    private String contenido;
    private RemitenteTipo remitenteTipo;
    private Long remitenteId;
    private LocalDateTime fechaEnvio;
    private boolean leido;

    public MensajeDTO(Long id, String contenido, RemitenteTipo remitenteTipo, Long remitenteId, LocalDateTime fechaEnvio, boolean leido) {
        this.id = id;
        this.contenido = contenido;
        this.remitenteTipo = remitenteTipo;
        this.remitenteId = remitenteId;
        this.fechaEnvio = fechaEnvio;
        this.leido = leido;
    }
    
}
