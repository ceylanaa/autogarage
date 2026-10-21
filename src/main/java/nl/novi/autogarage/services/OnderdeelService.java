package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.OnderdeelRequestDto;
import nl.novi.autogarage.dtos.response.OnderdeelResponseDto;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.Onderdeel;
import nl.novi.autogarage.repositories.OnderdeelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnderdeelService {

    private final OnderdeelRepository onderdeelRepository;

    public OnderdeelService(OnderdeelRepository onderdeelRepository) {
        this.onderdeelRepository = onderdeelRepository;
    }

    public List<OnderdeelResponseDto> getAllOnderdelen() {
        List<OnderdeelResponseDto> dtos = new ArrayList<>();
        for (Onderdeel onderdeel : onderdeelRepository.findAll()) {
            dtos.add(onderdeelToResponseDto(onderdeel));
        }
        return dtos;
    }

    public OnderdeelResponseDto getOnderdeelById(Long id) {
        return onderdeelToResponseDto(findOnderdeel(id));
    }

    public OnderdeelResponseDto createOnderdeel(OnderdeelRequestDto dto) {
        Onderdeel onderdeel = new Onderdeel();
        vulOnderdeel(onderdeel, dto);
        return onderdeelToResponseDto(onderdeelRepository.save(onderdeel));
    }

    public OnderdeelResponseDto updateOnderdeel(Long id, OnderdeelRequestDto dto) {
        Onderdeel onderdeel = findOnderdeel(id);
        vulOnderdeel(onderdeel, dto);
        return onderdeelToResponseDto(onderdeelRepository.save(onderdeel));
    }

    public void deleteOnderdeel(Long id) {
        if (!onderdeelRepository.existsById(id)) {
            throw new RecordNotFoundException("Onderdeel met id " + id + " niet gevonden");
        }
        onderdeelRepository.deleteById(id);
    }

    private Onderdeel findOnderdeel(Long id) {
        return onderdeelRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Onderdeel met id " + id + " niet gevonden"));
    }

    private void vulOnderdeel(Onderdeel onderdeel, OnderdeelRequestDto dto) {
        onderdeel.setNaam(dto.naam);
        onderdeel.setPrijsExclBtw(dto.prijsExclBtw);
        onderdeel.setVoorraad(dto.voorraad);
    }

    private OnderdeelResponseDto onderdeelToResponseDto(Onderdeel onderdeel) {
        OnderdeelResponseDto dto = new OnderdeelResponseDto();
        dto.id = onderdeel.getId();
        dto.naam = onderdeel.getNaam();
        dto.prijsExclBtw = onderdeel.getPrijsExclBtw();
        dto.voorraad = onderdeel.getVoorraad();
        return dto;
    }
}
