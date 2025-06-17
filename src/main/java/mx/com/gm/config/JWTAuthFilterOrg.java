
package mx.com.gm.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import mx.com.gm.service.AdminDetailsServ;
import mx.com.gm.service.DeportistaDetailsServ;
import mx.com.gm.service.InstructorDetailsServ;
import mx.com.gm.service.JWTUtils;
import mx.com.gm.service.OrganizacionDetailsServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class JWTAuthFilterOrg extends OncePerRequestFilter{
    @Autowired
    private JWTUtils jwt;

    @Autowired
    private DeportistaDetailsServ uds;
    @Autowired
    private InstructorDetailsServ ids;
    @Autowired
    private OrganizacionDetailsServ ods;
    @Autowired
    private AdminDetailsServ ads;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header=request.getHeader("Authorization");
        final String jwtToken;
        final String email;
        if(header==null || header.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = header.substring(7);
        email=jwt.extractUsername(jwtToken);
        System.out.println(email);
        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails user = null;
            try{
            user= uds.loadUserByUsername(email);
            }catch(Exception e){
                try{
                user= ids.loadUserByUsername(email);
                }catch(Exception ex){
                    try{
                    user= ods.loadUserByUsername(email);
                    }catch(Exception exe){
                        try{
                            user= ads.loadUserByUsername(email);
                            }catch(Exception exee){
                                throw new RuntimeException("Usuario no encontrado en ninguna categoría");
                            }
                    }
                }
                
            }
            if(jwt.isTokenValid(jwtToken, user)){
                SecurityContext sc=SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user,null,user.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                sc.setAuthentication(token);
                SecurityContextHolder.setContext(sc);
                }
            
        }
        filterChain.doFilter(request, response);
    }
    }
    
