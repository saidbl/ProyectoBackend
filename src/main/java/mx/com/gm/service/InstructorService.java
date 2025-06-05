
package mx.com.gm.service;

import java.io.IOException;
import java.util.List;
import mx.com.gm.domain.Instructor;
import mx.com.gm.dto.InstructorDTO;
import mx.com.gm.dto.ResponseAPI;
import org.springframework.web.multipart.MultipartFile;

public interface InstructorService {
    public List<Instructor> list();
    public ResponseAPI login(ResponseAPI login);
    public Instructor getById(Long id);
    public Instructor update(Long id, InstructorDTO idto, MultipartFile file)throws IOException;
     public Instructor listByIdDeportista(Long id);
}
