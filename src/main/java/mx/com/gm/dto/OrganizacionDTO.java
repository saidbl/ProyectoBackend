package mx.com.gm.dto;

import lombok.Data;

@Data
public class OrganizacionDTO {
    private Long id;
    private String email;
    private String password;
    private String nombre;
    private String telefono;
    private String direccion;
    private String nombreOrganizacion;
    private String tipo;  
    private String rol = "organizacion";
    private Long idDeporte;

}
