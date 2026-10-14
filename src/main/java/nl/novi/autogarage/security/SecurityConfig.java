package nl.novi.autogarage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        // alleen backoffice mag nieuwe gebruikers zien
                        .requestMatchers("/gebruikers/**").hasRole("BACKOFFICE")
                        .requestMatchers("/klanten/**").hasRole("KASSAMEDEWERKER")
                        .requestMatchers("/autos/**").hasRole("KASSAMEDEWERKER")
                        .requestMatchers(HttpMethod.PUT, "/keuringen/*/status").hasRole("MONTEUR")
                        .requestMatchers(HttpMethod.POST, "/keuringen").hasRole("KASSAMEDEWERKER")
                        .requestMatchers(HttpMethod.DELETE, "/keuringen/**").hasRole("KASSAMEDEWERKER")
                        .requestMatchers(HttpMethod.GET, "/keuringen/**").hasAnyRole("KASSAMEDEWERKER", "MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/reparaties/*/bon").hasRole("KASSAMEDEWERKER")
                        .requestMatchers(HttpMethod.PUT, "/reparaties/*/betaald").hasRole("KASSAMEDEWERKER")
                        .requestMatchers("/reparaties/**").hasAnyRole("MONTEUR", "KASSAMEDEWERKER")
                        // monteur moet onderdelen en handelingen kunnen opzoeken
                        .requestMatchers(HttpMethod.GET, "/onderdelen/**", "/reparatiehandelingen/**").hasAnyRole("BACKOFFICE", "MONTEUR")
                        .requestMatchers("/onderdelen/**").hasRole("BACKOFFICE")
                        .requestMatchers("/reparatiehandelingen/**").hasRole("BACKOFFICE")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
