package mx.com.gm.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatParticipanteId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long chat;
    private Long deportista;
}
