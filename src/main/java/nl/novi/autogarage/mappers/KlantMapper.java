package nl.novi.autogarage.mappers;

import nl.novi.autogarage.dtos.request.KlantRequestDto;
import nl.novi.autogarage.dtos.response.KlantResponseDto;
import nl.novi.autogarage.models.Klant;
import org.springframework.stereotype.Component;

@Component
public class KlantMapper {

    public KlantResponseDto toResponseDto(Klant klant) {
        KlantResponseDto dto = new KlantResponseDto();
        dto.setId(klant.getId());
        dto.setNaam(klant.getNaam());
        dto.setTelefoonnummer(klant.getTelefoonnummer());
        dto.setEmailadres(klant.getEmailadres());
        return dto;
    }

    public Klant toEntity(KlantRequestDto dto) {
        Klant klant = new Klant();
        klant.setNaam(dto.getNaam());
        klant.setTelefoonnummer(dto.getTelefoonnummer());
        klant.setEmailadres(dto.getEmailadres());
        return klant;
    }
}
