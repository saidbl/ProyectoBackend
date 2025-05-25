package mx.com.gm.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import lombok.Data;

@Data
@Entity
public class ChatParticipante implements Serializable{
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ChatParticipanteId id;
    @ManyToOne
    @MapsId("chat")
    @JsonBackReference
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @MapsId("deportista")
    @JoinColumn(name = "deportista_id")
    private Deportista deportista;

    public ChatParticipante(Chat chat, Deportista deportista) {
        this.chat = chat;
        this.deportista = deportista;
        this.id = new ChatParticipanteId(chat.getId(), deportista.getId());
    }
    


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatParticipante)) return false;
        ChatParticipante that = (ChatParticipante) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    
}

