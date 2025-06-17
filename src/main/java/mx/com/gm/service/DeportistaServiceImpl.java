package mx.com.gm.service;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import mx.com.gm.dao.AdminDao;
import mx.com.gm.dao.CheckinRutinaDao;
import mx.com.gm.dao.DeporteDao;
import mx.com.gm.dao.DeportistaDao;
import mx.com.gm.dao.InstructorDao;
import mx.com.gm.dao.MedicionFisicaDao;
import mx.com.gm.dao.ObjetivoRendimientoDao;
import mx.com.gm.dao.OrganizacionDao;
import mx.com.gm.dao.PosicionDao;
import mx.com.gm.dao.RegistroRendimientoDao;
import mx.com.gm.domain.Deporte;
import mx.com.gm.domain.Deportista;
import mx.com.gm.domain.Instructor;
import mx.com.gm.domain.MedicionFisica;
import mx.com.gm.domain.ObjetivoRendimiento;
import mx.com.gm.domain.Posicion;
import mx.com.gm.domain.RegistroRendimiento;
import mx.com.gm.dto.DeportistaDTO;
import mx.com.gm.dto.DeportistaMedicionDTO;
import mx.com.gm.dto.DeportistaRendimiento;
import mx.com.gm.dto.EvolucionFisicaDTO;
import mx.com.gm.dto.PosicionGeneroDTO;
import mx.com.gm.dto.ProgresoObjetivoDTO;
import mx.com.gm.dto.ResponseAPI;
import mx.com.gm.dto.ResumenAtletasDTO;
import mx.com.gm.dto.TipoRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DeportistaServiceImpl implements DeportistaService{
    @Autowired
    DeportistaDao ddao;
    @Autowired
    DeporteDao depdao;
    @Autowired
    InstructorDao idao;
    @Autowired 
    RegistroRendimientoDao rrdao;
    @Autowired
    OrganizacionDao odao;
    @Autowired
    ObjetivoRendimientoDao ordao;
    @Autowired
    CheckinRutinaDao chrdao;
    @Autowired 
    MedicionFisicaDao mfdao;
    @Autowired 
    ObjetivoRendimientoServiceImpl ori;
    @Autowired
    FileStorageService fsservice;
    @Autowired
    PosicionDao pdao;
    @Autowired
    AdminDao adao;

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
            response.setIdInstructor(user.getInstructor().getId());
            response.setApellido(user.getApellido());
            response.setNombre(user.getNombre());
            response.setExpirationTime("24Hrs");
            response.setMessage("Acceso Exitoso");
            response.setNombre(user.getNombre());
            response.setApellido(user.getApellido());
            response.setFotoPerfil(user.getFotoPerfil());
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
    public List<DeportistaRendimiento> listByIdInstructorObjRendCheck(Long id) {
        List <Deportista> deportistas = ddao.findByInstructorId(id);
        List<DeportistaRendimiento> drendimiento= new ArrayList<>();
        for(Deportista deportista:deportistas){
            DeportistaRendimiento dr= new DeportistaRendimiento();
            dr.setDeportista(deportista);
            List<RegistroRendimiento> rr = rrdao.findByDeportistaId(deportista.getId());
            dr.setRegistrosRendimiento(rr);
            List<ObjetivoRendimiento> totales = ordao.findByDeportistaId(deportista.getId());
            dr.setObjetivosTotales(totales);
            List<ObjetivoRendimiento> incomp = ordao.findByDeportistaIdAndCompletadoFalse(deportista.getId());
            dr.setObjetivosIncompletos(incomp);
            Long rutinasCompletadas = chrdao.countByDeportistaIdAndEstado(deportista.getId());
            Long rutinasTotales = chrdao.countByDeportistaId(deportista.getId());
            dr.setTotalRutinas(rutinasTotales);
            dr.setRutinasCompletadas(rutinasCompletadas);
            LocalDate fechaFin = LocalDate.now();
            LocalDate fechaInicio = fechaFin.minusMonths(3);
            List<MedicionFisica> ef = mfdao
                .findByDeportistaAndRangoFechas(deportista.getId(), fechaInicio, fechaFin);
            List<EvolucionFisicaDTO> efisica = ef.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
            dr.setEvolucionFisica(efisica);
            List<ProgresoObjetivoDTO> podto = ori.obtenerProgresoObjetivos(deportista.getId());
            dr.setProgresoObjetivo(podto);
            drendimiento.add(dr);
        }
        return drendimiento;
    }
    private EvolucionFisicaDTO convertirADTO(MedicionFisica medicion) {
        EvolucionFisicaDTO dto = new EvolucionFisicaDTO();
        dto.setFecha(medicion.getFecha());
        dto.setPeso(medicion.getPeso());
        dto.setImc(medicion.getImc());
        dto.setMasaMuscular(medicion.getMasaMuscular());
        dto.setPorcentajeGrasa(medicion.getPorcentajeGrasa());
        dto.setClasificacionIMC(clasificarIMC(medicion.getImc()));
        return dto;
    }
     private String clasificarIMC(Double imc) {
        if (imc == null) return "No calculado";
        if (imc < 18.5) return "Bajo peso";
        if (imc < 25) return "Normal";
        if (imc < 30) return "Sobrepeso";
        return "Obesidad";
    }
     
    @Override
    public ResumenAtletasDTO obtenerResumenAtletas(Long instructorId) {
    long masculinos = ddao.countMasculinosByInstructorId(instructorId);
    long femeninos = ddao.countFemeninosByInstructorId(instructorId);
    double edadGeneral = Optional.ofNullable(ddao.getEdadPromedioGeneral(instructorId))
                              .orElse(0.0);
    double edadHombres = Optional.ofNullable(ddao.getEdadPromedioHombres(instructorId))
                              .orElse(0.0);
    double edadMujeres = Optional.ofNullable(ddao.getEdadPromedioMujeres(instructorId))
                              .orElse(0.0);
    Map<String, PosicionGeneroDTO> distribucionMap = new LinkedHashMap<>();
    List<Object[]> rawData = ddao.getDistribucionPosicionGenero(instructorId);
    for (Object[] fila : rawData) {
        String posicion = (String) fila[0];
        String genero = (String) fila[1];
        Long cantidad = (Long) fila[2];
        
        PosicionGeneroDTO dto = distribucionMap.getOrDefault(posicion, new PosicionGeneroDTO(posicion, 0L, 0L));
        if ("Masculino".equals(genero)) {
            dto = new PosicionGeneroDTO(posicion, cantidad, dto.getMujeres());
        } else {
            dto = new PosicionGeneroDTO(posicion, dto.getHombres(), cantidad);
        }
        distribucionMap.put(posicion, dto);
    }
    
    return new ResumenAtletasDTO(
        masculinos,
        femeninos,
        edadGeneral,
        edadHombres,
        edadMujeres,
        new ArrayList<>(distribucionMap.values())
    );
}

    @Override
    public Deportista getById(Long id) {
        Deportista d = ddao.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Instructor no encontrado"));
       return d;
    }

    @Override
    public Deportista update(Long id, DeportistaDTO idto, MultipartFile file) throws IOException {
        Deportista d = ddao.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Instructor no encontrado"));
       Deporte dep = depdao.findById(idto.getIdDeporte())
               .orElseThrow(() -> new EntityNotFoundException("Instructor no encontrado"));
       if (file != null && !file.isEmpty()) {
            TipoRecurso tipo = TipoRecurso.fromContentType(file.getContentType());
            String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String rutaArchivo = fsservice.guardarArchivo(
            file.getInputStream(), 
            tipo.getNombreCarpeta(), 
            nombreArchivo
        );
            fsservice.eliminarArchivo(d.getFotoPerfil());
            d.setFotoPerfil(rutaArchivo);
        }
       d.setActivo(idto.getActivo());
       d.setApellido(idto.getApellido());
       d.setDeporte(dep);
       d.setDireccion(idto.getDireccion());
       d.setEmail(idto.getEmail());
       d.setFechaNacimiento(idto.getFechaNacimiento());
       d.setFechaRegistro(idto.getFechaRegistro());
       d.setNombre(idto.getNombre());
       d.setTelefono(idto.getTelefono());
       return ddao.save(d);
    }

    @Override
    public Deportista add(DeportistaMedicionDTO dto)throws IOException {
        if (idao.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (odao.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (ddao.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (adao.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        TipoRecurso tipo = TipoRecurso.fromContentType(dto.getFotoPerfil().getContentType());
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de archivo no soportado: " + dto.getFotoPerfil().getContentType());
        }
        String nombreArchivo = UUID.randomUUID() + "_" + dto.getFotoPerfil().getOriginalFilename();
        String rutaArchivo = fsservice.guardarArchivo(
            dto.getFotoPerfil().getInputStream(), 
            tipo.getNombreCarpeta(), 
            nombreArchivo
        );
        Instructor i = idao.findById(dto.getIdInstructor())
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        Deporte d = depdao.findById(dto.getIdDeporte())
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        Posicion p = pdao.findById(dto.getIdPosicion())
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        Deportista deportista = new Deportista();
        deportista.setNombre(dto.getNombre());
        deportista.setApellido(dto.getApellido());
        deportista.setEmail(dto.getEmail());
        deportista.setPassword(pe.encode(dto.getPassword()));
        deportista.setDeporte(d);
        deportista.setPosicion(p);
        deportista.setInstructor(i);
        deportista.setGenero(dto.getGenero());
        deportista.setEstatura(dto.getMedicion().getEstatura());
        deportista.setFechaNacimiento(dto.getFechaNacimiento());
        deportista.setTelefono(dto.getTelefono());
        deportista.setDireccion(dto.getDireccion());
        deportista.setRol("deportista");
        deportista.setFechaRegistro(new Date());
        deportista.setActivo(true);
        deportista.setFotoPerfil(rutaArchivo);
        Deportista deportistaGuardado = ddao.save(deportista);
        MedicionFisica medicion = new MedicionFisica();
        medicion.setDeportista(deportistaGuardado);
        medicion.setFecha(LocalDate.parse(dto.getMedicion().getFecha()));
        medicion.setPeso(dto.getMedicion().getPeso());
        medicion.setEstatura(dto.getMedicion().getEstatura());
        medicion.setPorcentajeGrasa(dto.getMedicion().getPorcentajeGrasa());
        medicion.setMasaMuscular(dto.getMedicion().getMasaMuscular());
        medicion.setCircunferenciaBrazo(dto.getMedicion().getCircunferenciaBrazo());
        medicion.setCircunferenciaCintura(dto.getMedicion().getCircunferenciaCintura());
        medicion.setCircunferenciaCadera(dto.getMedicion().getCircunferenciaCadera());
        medicion.setPresionArterial(dto.getMedicion().getPresionArterial());
        medicion.setFrecuenciaCardiacaReposo(dto.getMedicion().getFrecuenciaCardiacaReposo());
        medicion.setNotas(dto.getMedicion().getNotas());
        mfdao.save(medicion);
        return deportistaGuardado;
    }
    
    
}
