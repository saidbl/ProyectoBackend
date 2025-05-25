package mx.com.gm.domain;


public enum ChatTipo {
    INSTRUCTOR_DEPORTISTA("Chat individual Instructor-Deportista"),
    DEPORTISTA_INSTRUCTOR("Chat individual Deportista-Instructor"),
    INSTRUCTOR_ORGANIZACION("Chat individual Instructor-Organización"),
    ORGANIZACION_INSTRUCTOR("Chat individual Organización-Instructor"),
    EQUIPO("Chat grupal de equipo");

    private final String descripcion;

    ChatTipo(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
