package mx.com.gm.web;

import mx.com.gm.dto.MensajeDTO;
import mx.com.gm.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MensajeController {
    @Autowired
    MensajeService mservice;
    

     @GetMapping("/mensajes/{chatId}")
    public ResponseEntity<Page<MensajeDTO>> getMensajesPaginados(
            @PathVariable Long chatId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaEnvio").descending());
        return ResponseEntity.ok(mservice.obtenerMensajesPaginados(chatId, pageable));
    }
    @PostMapping("/{chatId}/leer")
    public ResponseEntity<Void> marcarComoLeidos(
            @PathVariable Long chatId,
            @RequestHeader("X-Usuario-Id") Long usuarioId) {
        mservice.marcarMensajesComoLeidos(chatId, usuarioId);
        return ResponseEntity.ok().build();
    }
}
