package mx.com.gm.domain;

public enum RemitenteTipo {
    INSTRUCTOR("Instructor"),
    DEPORTISTA("Deportista"),
    ORGANIZACION("Organización");

    private final String nombre;

    RemitenteTipo(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }
}
