package nl.novi.autogarage.mappers;

import nl.novi.autogarage.dtos.response.GebruikerResponseDto;
import nl.novi.autogarage.models.Gebruiker;
import org.springframework.stereotype.Component;

@Component
public class GebruikerMapper {

    public GebruikerResponseDto toResponseDto(Gebruiker gebruiker) {
        GebruikerResponseDto dto = new GebruikerResponseDto();
        dto.setId(gebruiker.getId());
        dto.setGebruikersnaam(gebruiker.getGebruikersnaam());
        dto.setRolNaam(gebruiker.getRol().getNaam());
        return dto;
    }
}
