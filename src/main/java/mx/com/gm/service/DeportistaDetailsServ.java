package mx.com.gm.service;

import mx.com.gm.dao.DeportistaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeportistaDetailsServ implements UserDetailsService{

     @Autowired
    private DeportistaDao ddao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ddao.findByEmail(username)
                   .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));
    }
    }
