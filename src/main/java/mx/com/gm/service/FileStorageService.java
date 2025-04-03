package mx.com.gm.service;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;



@Service
public class FileStorageService {
    private final Path rootLocation;

    public FileStorageService(@Value("${app.upload.dir:uploads}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de uploads", e);
        }
    }

    public String guardarArchivo(InputStream inputStream, String subdirectorio, String nombreArchivo) throws IOException {
        Path destino = this.rootLocation.resolve(subdirectorio).resolve(nombreArchivo).normalize();
        
        // Asegurar que el path está dentro del directorio raíz
        if (!destino.getParent().startsWith(this.rootLocation)) {
            throw new IOException("No se puede almacenar el archivo fuera del directorio raíz");
        }
        
        Files.createDirectories(destino.getParent());
        Files.copy(inputStream, destino, StandardCopyOption.REPLACE_EXISTING);
        
        return subdirectorio + "/" + nombreArchivo;
    }

    public Resource cargarArchivo(String rutaRelativa) throws MalformedURLException {
        Path archivo = this.rootLocation.resolve(rutaRelativa).normalize();
        Resource recurso = new UrlResource(archivo.toUri());
        
        if (recurso.exists() || recurso.isReadable()) {
            return recurso;
        } else {
            throw new RuntimeException("No se pudo leer el archivo: " + rutaRelativa);
        }
    }

    public void eliminarArchivo(String rutaRelativa) throws IOException {
        Path archivo = this.rootLocation.resolve(rutaRelativa).normalize();
        Files.deleteIfExists(archivo);
    }
}
