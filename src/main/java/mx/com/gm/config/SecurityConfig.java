package mx.com.gm.config;

import mx.com.gm.service.DeportistaDetailsServ;
import mx.com.gm.service.InstructorDetailsServ;
import mx.com.gm.service.OrganizacionDetailsServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTAuthFilter jwtDeportista;

    @Autowired
    private JWTAuthFilterOrg jwtOrganizacion;
    
    @Autowired
    private JWTAuthFilterInst jwtInstructor;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, 
                                                   CustomAuthenticationProvider customAuthenticationProvider) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/topic/**").permitAll()
                        .requestMatchers("/chat.send/**").permitAll()
                        .requestMatchers("/auth/**", "/public/**").permitAll()
                        .requestMatchers("/auth/login/deportista/**").permitAll()
                        .requestMatchers("/auth/login/organizacion/**").permitAll()
                        .requestMatchers("/auth/login/instructor/**").permitAll()
                        .requestMatchers("/rutinas/**").hasAnyAuthority("instructor")
                        .requestMatchers("/posiciones/**").hasAnyAuthority("instructor")
                        .requestMatchers("/deportistas/**").hasAnyAuthority("instructor")
                        .requestMatchers("/rutinaJugador/**").hasAnyAuthority("instructor")
                        .requestMatchers("/equipos/**").hasAnyAuthority("instructor","organizacion","deportista")
                        .requestMatchers("/eventosEquipo/**").hasAnyAuthority("instructor")
                        .requestMatchers("/api/**").hasAnyAuthority("instructor")
                        .requestMatchers("/ejercicios/**").hasAnyAuthority("instructor")
                        .requestMatchers("/video/**").permitAll()
                        .requestMatchers("/eventosFuturosDep/**").hasAnyAuthority("deportista")
                        .requestMatchers("/rutinasdeportista/**").hasAnyAuthority("deportista")
                        .requestMatchers("/checkin/**").hasAnyAuthority("deportista")
                        .requestMatchers("/equiposDeportista/**").hasAnyAuthority("deportista")
                        .requestMatchers("organizacion/crear").hasAnyAuthority("organizacion")
                        .requestMatchers("/image/**").permitAll()
                        .requestMatchers("/eventosDeportista").hasAnyAuthority("deportista")
                        .requestMatchers("/eventoFecha/**").permitAll()
                        .requestMatchers("/eventosFuturosOrg/**").hasAnyAuthority("organizacion")
                        .requestMatchers("/actualizarEvento/**").hasAnyAuthority("organizacion")
                        .requestMatchers("/eliminarEvento/**").hasAnyAuthority("organizacion")
                        .requestMatchers("/proximosEquipos").hasAnyAuthority("organizacion")
                        .requestMatchers("/generalesorg/**").hasAnyAuthority("organizacion")
                        .requestMatchers("/obtenerOrg/**").hasAnyAuthority("organizacion")
                        .requestMatchers("/deportista/**").hasAnyAuthority("deportista","instructor")
                        .requestMatchers("/agregarMetrica/**").hasAnyAuthority("deportista")
                        .requestMatchers("/agregarRecord/**").hasAnyAuthority("deportista")
                        .requestMatchers("/recordsJugador/**").hasAnyAuthority("deportista")
                        .requestMatchers("/agregarGoal/**").hasAnyAuthority("deportista")
                        .requestMatchers("/goalsJugador/**").hasAnyAuthority("deportista")
                        .requestMatchers("/evolucion/**").hasAnyAuthority("deportista")
                        .requestMatchers("/cumplimiento/**").hasAnyAuthority("deportista")
                        .requestMatchers("/progresoObjetivos/**").hasAnyAuthority("deportista")
                        .requestMatchers("/completadoGoal/**").permitAll()
                        .requestMatchers("/desasociar/**").hasAnyAuthority("instructor")
                        .requestMatchers("/instructor").hasAnyAuthority("instructor")
                        .requestMatchers("/estadisticasInstructor/**").hasAnyAuthority("instructor")
                        .requestMatchers("/eliminarGoal/**").hasAnyAuthority("deportista")
                        .requestMatchers("/addMedicion/**").hasAnyAuthority("deportista")
                        .requestMatchers("/listMedicion/**").hasAnyAuthority("deportista")
                        .requestMatchers("/ultimaMedicion/**").hasAnyAuthority("deportista")
                        .requestMatchers("/equiposEvento/**").hasAnyAuthority("deportista")
                        .requestMatchers("/usuario/**").permitAll()
                        .requestMatchers("/mensajes/**").permitAll()
                        .requestMatchers("/instructorOrg/**").hasAnyAuthority("organizacion")
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(customAuthenticationProvider) 
                .addFilterBefore(jwtOrganizacion, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtDeportista, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtInstructor, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(DeportistaDetailsServ deportistaDetailsServ,
                                                                     OrganizacionDetailsServ organizacionDetailsServ,
                                                                     InstructorDetailsServ instructorDetailsServ,
                                                                     PasswordEncoder passwordEncoder) {
        return new CustomAuthenticationProvider(deportistaDetailsServ, organizacionDetailsServ,instructorDetailsServ,passwordEncoder);
    }
}
