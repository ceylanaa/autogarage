package nl.novi.autogarage.mappers;

import nl.novi.autogarage.dtos.request.OnderdeelRequestDto;
import nl.novi.autogarage.dtos.response.OnderdeelResponseDto;
import nl.novi.autogarage.models.Onderdeel;
import org.springframework.stereotype.Component;

@Component
public class OnderdeelMapper {

    public OnderdeelResponseDto toResponseDto(Onderdeel onderdeel) {
        OnderdeelResponseDto dto = new OnderdeelResponseDto();
        dto.setId(onderdeel.getId());
        dto.setNaam(onderdeel.getNaam());
        dto.setPrijsExclBtw(onderdeel.getPrijsExclBtw());
        dto.setVoorraad(onderdeel.getVoorraad());
        return dto;
    }

    // vult een nieuw of bestaand onderdeel met de gegevens uit het request
    public void vulEntity(Onderdeel onderdeel, OnderdeelRequestDto dto) {
        onderdeel.setNaam(dto.getNaam());
        onderdeel.setPrijsExclBtw(dto.getPrijsExclBtw());
        onderdeel.setVoorraad(dto.getVoorraad());
    }
}
