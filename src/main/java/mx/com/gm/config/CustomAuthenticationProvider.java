package mx.com.gm.config;

import mx.com.gm.service.DeportistaDetailsServ;
import mx.com.gm.service.InstructorDetailsServ;
import mx.com.gm.service.OrganizacionDetailsServ;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final DeportistaDetailsServ deportistaDetailsServ;
    private final OrganizacionDetailsServ organizacionDetailsServ;
    private final InstructorDetailsServ instructorDetailsServ;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(DeportistaDetailsServ deportistaDetailsServ,
                                        OrganizacionDetailsServ organizacionDetailsServ,
                                        InstructorDetailsServ instructorDetailsServ,
                                        PasswordEncoder passwordEncoder) {
        this.deportistaDetailsServ = deportistaDetailsServ;
        this.organizacionDetailsServ = organizacionDetailsServ;
        this.instructorDetailsServ = instructorDetailsServ;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = null;

        // Intentamos autenticar primero como Deportista
        try {
            user = deportistaDetailsServ.loadUserByUsername(username);
        } catch (Exception e) {
            // Si no existe como Deportista, intentamos como Organización
            try {
                user = organizacionDetailsServ.loadUserByUsername(username);
            } catch (Exception ex) {
                try {
                    user = instructorDetailsServ.loadUserByUsername(username);
                } catch (Exception exe){
                    throw new RuntimeException("Usuario no encontrado en ninguna categoría");
                }
            }
        }
        System.out.println(user.getPassword());
        System.out.println("Bien");
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        } else {
            System.out.println("Error en password");
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}