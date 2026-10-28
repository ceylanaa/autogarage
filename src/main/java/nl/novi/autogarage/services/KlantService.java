package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.KlantRequestDto;
import nl.novi.autogarage.dtos.response.KlantResponseDto;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.Klant;
import nl.novi.autogarage.repositories.KlantRepository;
import nl.novi.autogarage.mappers.KlantMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KlantService {

    private final KlantRepository klantRepository;
    private final KlantMapper klantMapper;

    public KlantService(KlantRepository klantRepository, KlantMapper klantMapper) {
        this.klantRepository = klantRepository;
        this.klantMapper = klantMapper;
    }

    public List<KlantResponseDto> getAllKlanten() {
        List<KlantResponseDto> dtos = new ArrayList<>();
        for (Klant klant : klantRepository.findAll()) {
            dtos.add(klantMapper.toResponseDto(klant));
        }
        return dtos;
    }

    public KlantResponseDto getKlantById(Long id) {
        return klantMapper.toResponseDto(findKlant(id));
    }

    public KlantResponseDto createKlant(KlantRequestDto dto) {
        Klant klant = klantMapper.toEntity(dto);
        return klantMapper.toResponseDto(klantRepository.save(klant));
    }

    public KlantResponseDto updateKlant(Long id, KlantRequestDto dto) {
        Klant klant = findKlant(id);
        klant.setNaam(dto.getNaam());
        klant.setTelefoonnummer(dto.getTelefoonnummer());
        klant.setEmailadres(dto.getEmailadres());
        return klantMapper.toResponseDto(klantRepository.save(klant));
    }

    public void deleteKlant(Long id) {
        if (!klantRepository.existsById(id)) {
            throw new RecordNotFoundException("Klant met id " + id + " niet gevonden");
        }
        klantRepository.deleteById(id);
    }

    private Klant findKlant(Long id) {
        return klantRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Klant met id " + id + " niet gevonden"));
    }
}
