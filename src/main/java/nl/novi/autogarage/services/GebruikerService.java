package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.GebruikerRequestDto;
import nl.novi.autogarage.dtos.response.GebruikerResponseDto;
import nl.novi.autogarage.exceptions.BadRequestException;
import nl.novi.autogarage.models.Gebruiker;
import nl.novi.autogarage.models.Rol;
import nl.novi.autogarage.repositories.GebruikerRepository;
import nl.novi.autogarage.repositories.RolRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GebruikerService {

    private final GebruikerRepository gebruikerRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public GebruikerService(GebruikerRepository gebruikerRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.gebruikerRepository = gebruikerRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public GebruikerResponseDto registerGebruiker(GebruikerRequestDto dto) {
        if (gebruikerRepository.existsByGebruikersnaam(dto.getGebruikersnaam())) {
            throw new BadRequestException("Gebruikersnaam " + dto.getGebruikersnaam() + " is al in gebruik");
        }
        Rol rol = rolRepository.findByNaam(dto.getRolNaam().toUpperCase())
                .orElseThrow(() -> new BadRequestException("Rol " + dto.getRolNaam() + " bestaat niet"));

        Gebruiker gebruiker = new Gebruiker();
        gebruiker.setGebruikersnaam(dto.getGebruikersnaam());
        gebruiker.setWachtwoord(passwordEncoder.encode(dto.getWachtwoord()));
        gebruiker.setRol(rol);
        return toDto(gebruikerRepository.save(gebruiker));
    }

    public List<GebruikerResponseDto> getAllGebruikers() {
        List<GebruikerResponseDto> dtos = new ArrayList<>();
        for (Gebruiker g : gebruikerRepository.findAll()) {
            dtos.add(toDto(g));
        }
        return dtos;
    }

    private GebruikerResponseDto toDto(Gebruiker gebruiker) {
        GebruikerResponseDto dto = new GebruikerResponseDto();
        dto.setId(gebruiker.getId());
        dto.setGebruikersnaam(gebruiker.getGebruikersnaam());
        dto.setRolNaam(gebruiker.getRol().getNaam());
        return dto;
    }
}
