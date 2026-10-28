package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.OnderdeelRequestDto;
import nl.novi.autogarage.dtos.response.OnderdeelResponseDto;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.Onderdeel;
import nl.novi.autogarage.repositories.OnderdeelRepository;
import nl.novi.autogarage.mappers.OnderdeelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnderdeelService {

    private final OnderdeelRepository onderdeelRepository;
    private final OnderdeelMapper onderdeelMapper;

    public OnderdeelService(OnderdeelRepository onderdeelRepository, OnderdeelMapper onderdeelMapper) {
        this.onderdeelRepository = onderdeelRepository;
        this.onderdeelMapper = onderdeelMapper;
    }

    public List<OnderdeelResponseDto> getAllOnderdelen() {
        List<OnderdeelResponseDto> dtos = new ArrayList<>();
        for (Onderdeel onderdeel : onderdeelRepository.findAll()) {
            dtos.add(onderdeelMapper.toResponseDto(onderdeel));
        }
        return dtos;
    }

    public OnderdeelResponseDto getOnderdeelById(Long id) {
        return onderdeelMapper.toResponseDto(findOnderdeel(id));
    }

    public OnderdeelResponseDto createOnderdeel(OnderdeelRequestDto dto) {
        Onderdeel onderdeel = new Onderdeel();
        onderdeelMapper.vulEntity(onderdeel, dto);
        return onderdeelMapper.toResponseDto(onderdeelRepository.save(onderdeel));
    }

    public OnderdeelResponseDto updateOnderdeel(Long id, OnderdeelRequestDto dto) {
        Onderdeel onderdeel = findOnderdeel(id);
        onderdeelMapper.vulEntity(onderdeel, dto);
        return onderdeelMapper.toResponseDto(onderdeelRepository.save(onderdeel));
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
}
