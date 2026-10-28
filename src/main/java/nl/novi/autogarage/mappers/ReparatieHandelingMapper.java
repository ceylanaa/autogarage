package nl.novi.autogarage.mappers;

import nl.novi.autogarage.dtos.request.ReparatieHandelingRequestDto;
import nl.novi.autogarage.dtos.response.ReparatieHandelingResponseDto;
import nl.novi.autogarage.models.ReparatieHandeling;
import org.springframework.stereotype.Component;

@Component
public class ReparatieHandelingMapper {

    public ReparatieHandelingResponseDto toResponseDto(ReparatieHandeling handeling) {
        ReparatieHandelingResponseDto dto = new ReparatieHandelingResponseDto();
        dto.setId(handeling.getId());
        dto.setNaam(handeling.getNaam());
        dto.setPrijsExclBtw(handeling.getPrijsExclBtw());
        return dto;
    }

    public void vulEntity(ReparatieHandeling handeling, ReparatieHandelingRequestDto dto) {
        handeling.setNaam(dto.getNaam());
        handeling.setPrijsExclBtw(dto.getPrijsExclBtw());
    }
}
