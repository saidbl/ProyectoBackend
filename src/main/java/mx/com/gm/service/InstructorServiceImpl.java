package mx.com.gm.service;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import mx.com.gm.dao.DeporteDao;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.InstructorDao;
import mx.com.gm.domain.Deporte;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Instructor;
import mx.com.gm.dto.InstructorDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.TipoRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorDao idao;
    @Autowired
    private AuthenticationManager am;
    
    @Autowired
    private DeporteDao ddao;
    
    @Autowired 
    private FileStorageService fsservice;
    
    @Autowired
    private DeportistaDao depdao;
    
    @Autowired
    private JWTUtils jwt;
    
    @Autowired
    private PasswordEncoder pe;
    @Override
    public List<Instructor> list() {
        return idao.findAll();
    }

    @Override
    public ResponseAPI login(ResponseAPI login) {
         ResponseAPI response = new ResponseAPI();
        try{
            am
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword()));
            var user = idao.findByEmail(login.getEmail()).orElseThrow();
            var jw=jwt.generateToken(user);
            var refreshToken = jwt.generateRefreshTokens(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jw);
            response.setId(user.getId());
            response.setRol(user.getRol());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setIdDeporte(user.getDeporte().getId());
            response.setFotoPerfil(user.getFotoPerfil());
            response.setNombre(user.getNombre());
            response.setApellido(user.getApellido());
            response.setMessage("Acceso Exitoso");
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Contraseña o email incorrecto");
        }
        return response;
    }

    @Override
    public Instructor getById(Long id) {
       Instructor i = idao.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Instructor no encontrado"));
       return i;
    }

    @Override
    public Instructor update(Long id, InstructorDTO idto, MultipartFile file)throws IOException {
       Instructor i = idao.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Instructor no encontrado"));
       Deporte d = ddao.findById(idto.getIdDeporte())
               .orElseThrow(() -> new EntityNotFoundException("Instructor no encontrado"));
       if (file != null && !file.isEmpty()) {
            TipoRecurso tipo = TipoRecurso.fromContentType(file.getContentType());
            String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String rutaArchivo = fsservice.guardarArchivo(
            file.getInputStream(), 
            tipo.getNombreCarpeta(), 
            nombreArchivo
        );
            fsservice.eliminarArchivo(i.getFotoPerfil());
            i.setFotoPerfil(rutaArchivo);
        }
       i.setApellido(idto.getApellido());
       i.setDeporte(d);
       i.setEmail(idto.getEmail());
       i.setEspecialidad(idto.getEspecialidad());
       i.setExperiencia(idto.getExperiencia());
       i.setNombre(idto.getNombre());
       i.setTelefono(idto.getTelefono());
       return idao.save(i);
    }

    @Override
    public Instructor listByIdDeportista(Long id) {
        Deportista deportista = depdao.findById(id)
            .orElseThrow(() -> new RuntimeException("Deportista no encontrado"));
        return deportista.getInstructor();
    }

    @Override
    public List<Instructor> listByDeporte(Long id) {
        return idao.findByDeporteId(id);
    }

    @Override
    public Instructor add(InstructorDTO idto, MultipartFile file) throws IOException {
        TipoRecurso tipo = TipoRecurso.fromContentType(file.getContentType());
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de archivo no soportado: " + file.getContentType());
        }
        String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String rutaArchivo = fsservice.guardarArchivo(
            file.getInputStream(), 
            tipo.getNombreCarpeta(), 
            nombreArchivo
        );
       Instructor i = new Instructor();
       Deporte d = ddao.findById(idto.getIdDeporte())
               .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
       i.setApellido(idto.getApellido());
       i.setDeporte(d);
       i.setEmail(idto.getEmail());
       i.setEspecialidad(idto.getEspecialidad());
       i.setExperiencia(idto.getExperiencia());
       i.setFotoPerfil(rutaArchivo);
       i.setNombre(idto.getNombre());
       String hashedPassword = pe.encode(idto.getPassword());
       i.setPassword(hashedPassword);
       i.setRol(idto.getRol());
       i.setTelefono(idto.getTelefono());
       Instructor ins = idao.save(i);
       return ins;
    }
    
}
