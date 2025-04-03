package mx.com.gm.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import mx.com.gm.dao.EjercicioRutinaDao;
import mx.com.gm.dao.RecursoRutinaDao;
import mx.com.gm.domain.EjercicioRutina;
import mx.com.gm.domain.RecursoRutina;
import mx.com.gm.dto.TipoRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class RecursoRutinaService {
    private final RecursoRutinaDao recursoRepository;
    private final EjercicioRutinaDao ejercicioRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public RecursoRutinaService(RecursoRutinaDao recursoRepository,
                              EjercicioRutinaDao ejercicioRepository,
                              FileStorageService fileStorageService) {
        this.recursoRepository = recursoRepository;
        this.ejercicioRepository = ejercicioRepository;
        this.fileStorageService = fileStorageService;
    }

    public RecursoRutina crearRecurso(Long ejercicioId, TipoRecurso tipo, String url, String descripcion) {
        EjercicioRutina ejercicio = ejercicioRepository.findById(ejercicioId)
            .orElseThrow(() -> new EntityNotFoundException("Ejercicio no encontrado con ID: " + ejercicioId));
        
        RecursoRutina recurso = new RecursoRutina();
        recurso.setEjercicioRutina(ejercicio);
        recurso.setTipo(tipo);
        recurso.setUrl(url);
        recurso.setDescripcion(descripcion);
        
        return recursoRepository.save(recurso);
    }

    public RecursoRutina subirArchivo(Long ejercicioId, MultipartFile archivo, String descripcion) throws IOException {
        TipoRecurso tipo = TipoRecurso.fromContentType(archivo.getContentType());
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de archivo no soportado: " + archivo.getContentType());
        }
        String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
        String rutaArchivo = fileStorageService.guardarArchivo(
            archivo.getInputStream(), 
            tipo.getNombreCarpeta(), 
            nombreArchivo
        );
        return crearRecurso(ejercicioId, tipo, rutaArchivo, descripcion);
    }
    public List<RecursoRutina> obtenerRecursosPorEjercicio(Long ejercicioId) {
        return recursoRepository.findByEjercicioRutinaId(ejercicioId);
    }
    public List<RecursoRutina> obtenerRecursosPorTipo(Long ejercicioId, TipoRecurso tipo) {
        return recursoRepository.findByEjercicioRutinaIdAndTipo(ejercicioId, tipo);
    }

    /**
     * Elimina un recurso (tanto el registro como el archivo físico)
     * @param recursoId
     * @throws java.io.IOException
     */
    public void eliminarRecurso(Long recursoId) throws IOException {
        RecursoRutina recurso = recursoRepository.findById(recursoId)
            .orElseThrow(() -> new EntityNotFoundException("Recurso no encontrado con ID: " + recursoId));
        
        // Eliminar archivo físico
        if (recurso.getTipo() != TipoRecurso.URL_EXTERNA) {
            fileStorageService.eliminarArchivo(recurso.getUrl());
        }
        
        // Eliminar registro de BD
        recursoRepository.delete(recurso);
    }
    public RecursoRutina actualizarDescripcion(Long recursoId, String nuevaDescripcion) {
        RecursoRutina recurso = recursoRepository.findById(recursoId)
            .orElseThrow(() -> new EntityNotFoundException("Recurso no encontrado con ID: " + recursoId));
        
        recurso.setDescripcion(nuevaDescripcion);
        return recursoRepository.save(recurso);
    }
    public boolean ejercicioTieneRecursos(Long ejercicioId) {
        return recursoRepository.existsByEjercicioRutinaId(ejercicioId);
    }
}
