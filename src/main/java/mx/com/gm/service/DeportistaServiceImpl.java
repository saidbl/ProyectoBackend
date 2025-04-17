
package mx.com.gm.service;

import java.util.*;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Rutina;
import mx.com.gm.dto.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DeportistaServiceImpl implements DeportistaService{
    @Autowired
    DeportistaDao ddao;

    @Autowired
    private AuthenticationManager am;
    
    @Autowired
    private JWTUtils jwt;
    
    @Autowired
    private PasswordEncoder pe;
    @Override
    public List<Deportista> list() {
        return ddao.findAll();
    }

    @Override
    public ResponseAPI login(ResponseAPI login) {
         ResponseAPI response = new ResponseAPI();
        try{
            am
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword()));
            var user = ddao.findByEmail(login.getEmail()).orElseThrow();
            var jw=jwt.generateToken(user);
            var refreshToken = jwt.generateRefreshTokens(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jw);
            response.setId(user.getId());
            response.setRol(user.getRol());
            response.setRefreshToken(refreshToken);
            response.setApellido(user.getApellido());
            response.setNombre(user.getNombre());
            response.setExpirationTime("24Hrs");
            response.setMessage("Acceso Exitoso");
            response.setIdDeporte(user.getDeporte().getId());
            response.setPosicion(user.getPosicion().getNombre());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Contraseña o email incorrecto");
        }
        return response;
    }

    @Override
    public List<Deportista> listByIdInstructor(Long id) {
        return ddao.findByInstructorId(id);
    }

    @Override
    public List<Rutina> listByDepostistaAndDia(Long idDeportista) {
        String diaSemana = obtenerDiaSemanaActual();
        return ddao.findRutinasByDeportistaAndDiaSemana(idDeportista, diaSemana);
    }
    private String obtenerDiaSemanaActual() {
        Map<Integer, String> diasSemana = Map.of(
            1, "Lunes",
            2, "Martes",
            3, "Miércoles",
            4, "Jueves",
            5, "Viernes",
            6, "Sábado",
            7, "Domingo"
        );
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_WEEK);
        return diasSemana.get(dia == 1 ? 7 : dia - 1);
    }
    
}
