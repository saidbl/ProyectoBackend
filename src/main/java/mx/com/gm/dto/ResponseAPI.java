package mx.com.gm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Instructor;
import mx.com.gm.domain.Organizacion;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ResponseAPI {
    private int StatusCode;
    private long id;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String username;
    private String password;
    private String email;
    private String rol;
    private Deportista deportista;
    private List<Deportista> deportistas;
    private Instructor instructor;
    private List<Instructor> instructores;
    private Organizacion org;
    private List<Organizacion> orgs;
    private Long idDeporte;
    private Long idInstructor;
    private boolean success;
    private String nombre;
    private String apellido;
    private String posicion;
    private String fotoPerfil;

   
}
