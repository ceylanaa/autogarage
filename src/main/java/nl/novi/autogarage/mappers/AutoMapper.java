package nl.novi.autogarage.mappers;

import nl.novi.autogarage.dtos.response.AutoResponseDto;
import nl.novi.autogarage.models.Auto;
import org.springframework.stereotype.Component;

@Component
public class AutoMapper {

    public AutoResponseDto toResponseDto(Auto auto) {
        AutoResponseDto dto = new AutoResponseDto();
        dto.setId(auto.getId());
        dto.setKenteken(auto.getKenteken());
        dto.setMerk(auto.getMerk());
        dto.setModel(auto.getModel());
        dto.setKlantId(auto.getKlant().getId());
        dto.setKlantNaam(auto.getKlant().getNaam());
        dto.setHeeftAutopapier(auto.getAutopapierNaam() != null);
        return dto;
    }
}
