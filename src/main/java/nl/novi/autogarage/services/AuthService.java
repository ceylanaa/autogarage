package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.LoginRequestDto;
import nl.novi.autogarage.dtos.response.AuthResponseDto;
import nl.novi.autogarage.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        // gooit BadCredentialsException als het niet klopt
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.gebruikersnaam, dto.wachtwoord));
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        AuthResponseDto response = new AuthResponseDto();
        response.token = jwtUtil.generateToken(userDetails);
        response.gebruikersnaam = userDetails.getUsername();
        response.rol = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        return response;
    }
}
