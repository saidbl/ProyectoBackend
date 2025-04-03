
package mx.com.gm.service;

import java.util.HashMap;
import java.util.List;
import mx.com.gm.dao.OrganizacionDao;
import mx.com.gm.domain.Organizacion;
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
            response.setIdDeporte(user.getDeporte().getId());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Contraseña o email incorrecto");
        }
        return response;
    }
    }
    
