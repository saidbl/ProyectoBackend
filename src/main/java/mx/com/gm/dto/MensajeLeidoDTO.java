/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.com.gm.dto;

import lombok.Data;

@Data
public class MensajeLeidoDTO {
    private Long chatId;
    private Long usuarioId;

    public MensajeLeidoDTO(Long chatId, Long usuarioId) {
        this.chatId = chatId;
        this.usuarioId = usuarioId;
    }
    
}
