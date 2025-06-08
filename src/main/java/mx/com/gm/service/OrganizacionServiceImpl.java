
package mx.com.gm.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import mx.com.gm.dao.OrganizacionDao;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.OrganizacionDTO;
import mx.com.gm.dto.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OrganizacionServiceImpl implements OrganizacionService{

    @Autowired
    OrganizacionDao odao;
    
    @Autowired
    private AuthenticationManager am;
    
    @Autowired
    private JWTUtils jwt;
    
    @Autowired
    private PasswordEncoder pe;
    
    @Override
    public List<Organizacion> list() {
        return odao.findAll();
    }

    @Override
    public ResponseAPI login(ResponseAPI login) {
        ResponseAPI response = new ResponseAPI();
        try{
            am
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword()));
            var user = odao.findByEmail(login.getEmail()).orElseThrow();
            var jw=jwt.generateToken(user);
            var refreshToken = jwt.generateRefreshTokens(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jw);
            response.setId(user.getId());
            response.setRol(user.getRol());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Acceso Exitoso");
            response.setNombre(user.getNombre());
            response.setFotoPerfil(user.getImagen());
            response.setIdDeporte(user.getDeporte().getId());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Contraseña o email incorrecto");
        }
        return response;
    }

    @Override
    public Organizacion getbyId(Long id) {
        return odao.findById(id) 
        .orElseThrow(() -> new EntityNotFoundException("Organización no encontrada"));
    }

    @Override
    public Organizacion update(Long id, OrganizacionDTO odto) {
        Organizacion oExistente = odao.findById(id)
            .orElseThrow(() -> new RuntimeException("Deporte no encontrada"));
        oExistente.setDireccion(odto.getDireccion());
        oExistente.setEmail(odto.getEmail());
        oExistente.setNombre(odto.getNombre());
        oExistente.setNombreOrganizacion(odto.getNombreOrganizacion());
        oExistente.setTelefono(odto.getTelefono());
        oExistente.setTipo(odto.getTipo());
        return odao.save(oExistente);
    }

    @Override
    public List<Organizacion> listByDeporte(Long id) {
        return odao.findByDeporteId(id);
    }
    }
    
