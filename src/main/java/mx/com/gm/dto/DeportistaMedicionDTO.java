package mx.com.gm.dto;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class DeportistaMedicionDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Long idInstructor;
    private Long idDeporte;
    private Long idPosicion;
    private String genero;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;
    private MultipartFile fotoPerfil;
    private MedicionDTO medicion;
}
