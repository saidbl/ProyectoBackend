package mx.com.gm.dto;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

@Data
public class DeportistaDTO {
    private Long id;

    private String email;

    private String password;

    private String nombre;

    private String apellido;

    private LocalDate fechaNacimiento;

    private String telefono;
    private String direccion;

    private String rol = "deportista";
    
    private String genero;
    
    private Integer estatura;
    
    private Double peso;
    
    private Date fechaRegistro;
    
    private Boolean activo = true;

    private Long idDeporte;

    private Long idInstructor;

    private Long idPosicion;
    private String fotoPerfil;
}
