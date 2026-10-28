package nl.novi.autogarage.mappers;

import nl.novi.autogarage.dtos.response.OverigeHandelingResponseDto;
import nl.novi.autogarage.dtos.response.ReparatieOnderdeelResponseDto;
import nl.novi.autogarage.dtos.response.ReparatieRegelResponseDto;
import nl.novi.autogarage.dtos.response.ReparatieResponseDto;
import nl.novi.autogarage.models.OverigeHandeling;
import nl.novi.autogarage.models.Reparatie;
import nl.novi.autogarage.models.ReparatieOnderdeel;
import nl.novi.autogarage.models.ReparatieRegel;
import org.springframework.stereotype.Component;

@Component
public class ReparatieMapper {

    public ReparatieResponseDto toResponseDto(Reparatie reparatie) {
        ReparatieResponseDto dto = new ReparatieResponseDto();
        dto.setId(reparatie.getId());
        dto.setKeuringId(reparatie.getKeuring().getId());
        dto.setReparatieDatum(reparatie.getReparatieDatum());
        dto.setStatus(reparatie.getStatus().name());
        dto.setBetaald(reparatie.isBetaald());
        for (ReparatieOnderdeel ro : reparatie.getOnderdelen()) {
            dto.getOnderdelen().add(onderdeelToDto(ro));
        }
        for (ReparatieRegel regel : reparatie.getHandelingen()) {
            dto.getHandelingen().add(regelToDto(regel));
        }
        for (OverigeHandeling overig : reparatie.getOverigeHandelingen()) {
            dto.getOverigeHandelingen().add(overigToDto(overig));
        }
        return dto;
    }

    public ReparatieOnderdeelResponseDto onderdeelToDto(ReparatieOnderdeel ro) {
        ReparatieOnderdeelResponseDto dto = new ReparatieOnderdeelResponseDto();
        dto.setId(ro.getId());
        dto.setOnderdeelId(ro.getOnderdeel().getId());
        dto.setNaam(ro.getOnderdeel().getNaam());
        dto.setPrijsExclBtw(ro.getOnderdeel().getPrijsExclBtw());
        dto.setAantal(ro.getAantal());
        return dto;
    }

    public ReparatieRegelResponseDto regelToDto(ReparatieRegel regel) {
        ReparatieRegelResponseDto dto = new ReparatieRegelResponseDto();
        dto.setId(regel.getId());
        dto.setReparatieHandelingId(regel.getReparatieHandeling().getId());
        dto.setNaam(regel.getReparatieHandeling().getNaam());
        dto.setPrijsExclBtw(regel.getReparatieHandeling().getPrijsExclBtw());
        return dto;
    }

    public OverigeHandelingResponseDto overigToDto(OverigeHandeling overig) {
        OverigeHandelingResponseDto dto = new OverigeHandelingResponseDto();
        dto.setId(overig.getId());
        dto.setOmschrijving(overig.getOmschrijving());
        dto.setPrijsExclBtw(overig.getPrijsExclBtw());
        return dto;
    }
}
