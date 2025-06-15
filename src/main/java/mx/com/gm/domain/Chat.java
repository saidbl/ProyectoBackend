package mx.com.gm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;

@Entity
@Data
public class Chat implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatTipo tipo;

    @ManyToOne
    private Deporte deporte;

    @ManyToOne
    private Instructor instructor;

    @ManyToOne
    private Deportista deportista;

    @ManyToOne
    private Organizacion organizacion;

    @ManyToOne
    private Equipo equipo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chat")
    private Set<ChatParticipante> participantes;

    private LocalDateTime fechaCreacion;
    
    public Chat() {
    this.participantes = new HashSet<>();
    
    
}
    public Long getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
