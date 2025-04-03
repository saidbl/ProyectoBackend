
package mx.com.gm.service;

import java.util.List;
import mx.com.gm.domain.Instructor;
import mx.com.gm.dto.ResponseAPI;

public interface InstructorService {
    public List<Instructor> list();
    public ResponseAPI login(ResponseAPI login);
}
