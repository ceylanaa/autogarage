package nl.novi.autogarage.mappers;

import nl.novi.autogarage.dtos.response.KeuringResponseDto;
import nl.novi.autogarage.models.Keuring;
import org.springframework.stereotype.Component;

@Component
public class KeuringMapper {

    public KeuringResponseDto toResponseDto(Keuring keuring) {
        KeuringResponseDto dto = new KeuringResponseDto();
        dto.setId(keuring.getId());
        dto.setAutoId(keuring.getAuto().getId());
        dto.setKenteken(keuring.getAuto().getKenteken());
        dto.setKeuringsDatum(keuring.getKeuringsDatum());
        dto.setTekortkomingen(keuring.getTekortkomingen());
        dto.setStatus(keuring.getStatus().name());
        dto.setKeuringsBedrag(Keuring.KEURINGS_BEDRAG);
        return dto;
    }
}
