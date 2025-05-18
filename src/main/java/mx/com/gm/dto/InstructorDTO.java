
package mx.com.gm.dto;

import lombok.Data;

@Data
public class InstructorDTO {
    private Long id;
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    private String especialidad;
    private int experiencia;
    private String rol = "instructor";
    private Long idDeporte;
    private String fotoPerfil;
}
