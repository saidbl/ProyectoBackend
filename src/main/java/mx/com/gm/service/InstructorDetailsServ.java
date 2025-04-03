package mx.com.gm.service;

import mx.com.gm.dao.InstructorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InstructorDetailsServ implements UserDetailsService{

    @Autowired
    private InstructorDao idao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return idao.findByEmail(username)
                   .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));
    }
    
}
