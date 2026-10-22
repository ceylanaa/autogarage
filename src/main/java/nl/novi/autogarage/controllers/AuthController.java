package nl.novi.autogarage.controllers;

import nl.novi.autogarage.dtos.request.GebruikerRequestDto;
import nl.novi.autogarage.dtos.request.LoginRequestDto;
import nl.novi.autogarage.dtos.response.AuthResponseDto;
import nl.novi.autogarage.dtos.response.GebruikerResponseDto;
import nl.novi.autogarage.services.AuthService;
import nl.novi.autogarage.services.GebruikerService;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class AuthController {

    private final AuthService authService;
    private final GebruikerService gebruikerService;

    public AuthController(AuthService authService, GebruikerService gebruikerService) {
        this.authService = authService;
        this.gebruikerService = gebruikerService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<GebruikerResponseDto> register(@Valid @RequestBody GebruikerRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gebruikerService.registerGebruiker(dto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @GetMapping("/gebruikers")
    public ResponseEntity<List<GebruikerResponseDto>> getAllGebruikers() {
        return ResponseEntity.ok(gebruikerService.getAllGebruikers());
    }
}
