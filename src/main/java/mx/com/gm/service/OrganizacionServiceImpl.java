
package mx.com.gm.service;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import mx.com.gm.dao.AdminDao;
import mx.com.gm.dao.DeporteDao;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.InstructorDao;
import mx.com.gm.dao.OrganizacionDao;
import mx.com.gm.domain.Deporte;
import mx.com.gm.domain.Organizacion;
import mx.com.gm.dto.OrganizacionDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.TipoRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OrganizacionServiceImpl implements OrganizacionService{

    @Autowired
    OrganizacionDao odao;
    
    @Autowired
    private DeporteDao ddao;
    
    @Autowired
    private AdminDao adao;
    
    @Autowired
    private DeportistaDao depdao;
    
    @Autowired
    InstructorDao idao;
    
    @Autowired
    private AuthenticationManager am;
    
    @Autowired
    private JWTUtils jwt;
    
    @Autowired
    private PasswordEncoder pe;
    
    @Autowired
    private FileStorageService fsservice;
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
            response.setFotoPerfil(user.getImagen());
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
    public Organizacion update(Long id, OrganizacionDTO odto, MultipartFile file)throws IOException {
        Organizacion o = odao.findById(id)
            .orElseThrow(() -> new RuntimeException("Deporte no encontrada"));
        if (file != null && !file.isEmpty()) {
            TipoRecurso tipo = TipoRecurso.fromContentType(file.getContentType());
            String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String rutaArchivo = fsservice.guardarArchivo(
            file.getInputStream(), 
            tipo.getNombreCarpeta(), 
            nombreArchivo
        );
            fsservice.eliminarArchivo(o.getImagen());
            o.setImagen(rutaArchivo);
        }
        o.setDireccion(odto.getDireccion());
        o.setEmail(odto.getEmail());
        o.setNombre(odto.getNombre());
        o.setNombreOrganizacion(odto.getNombreOrganizacion());
        o.setTelefono(odto.getTelefono());
        o.setTipo(odto.getTipo());
        return odao.save(o);
    }

    @Override
    public List<Organizacion> listByDeporte(Long id) {
        return odao.findByDeporteId(id);
    }

    @Override
    public Organizacion add(OrganizacionDTO idto, MultipartFile file) throws IOException {
        if (idao.existsByEmail(idto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (odao.existsByEmail(idto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (depdao.existsByEmail(idto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (adao.existsByEmail(idto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
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
       Organizacion o = new Organizacion();
       Deporte d = ddao.findById(idto.getIdDeporte())
               .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
       o.setDeporte(d);
       o.setDireccion(idto.getDireccion());
       o.setEmail(idto.getEmail());
       o.setImagen(rutaArchivo);
       o.setNombre(idto.getNombre());
       o.setNombreOrganizacion(idto.getNombreOrganizacion());
       String hashedPassword = pe.encode(idto.getPassword());
       o.setPassword(hashedPassword);
       o.setRol(idto.getRol());
       o.setTelefono(idto.getTelefono());
       o.setTipo(idto.getTipo());
       Organizacion or = odao.save(o);
       return or;
    }
    }
    
