package mx.com.gm.web;

import java.util.List;
import mx.com.gm.domain.Chat;
import mx.com.gm.domain.Mensaje;
import mx.com.gm.dto.ChatRequest;
import mx.com.gm.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/usuario/crear")
    public ResponseEntity<Chat> crearChat(@RequestBody ChatRequest request) {
        System.out.println(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(chatService.crearChatIndividual(request));
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Chat>> obtenerChatsUsuario(
        @PathVariable Long userId,
        @RequestParam String tipoUsuario) {
        
        return ResponseEntity.ok(chatService.obtenerChatsUsuario(userId, tipoUsuario));
    }  
     
}
