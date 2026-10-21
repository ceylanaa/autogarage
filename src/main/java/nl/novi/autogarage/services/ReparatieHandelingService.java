package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.ReparatieHandelingRequestDto;
import nl.novi.autogarage.dtos.response.ReparatieHandelingResponseDto;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.ReparatieHandeling;
import nl.novi.autogarage.repositories.ReparatieHandelingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReparatieHandelingService {

    private final ReparatieHandelingRepository repository;

    public ReparatieHandelingService(ReparatieHandelingRepository repository) {
        this.repository = repository;
    }

    public List<ReparatieHandelingResponseDto> getAllReparatieHandelingen() {
        List<ReparatieHandelingResponseDto> dtos = new ArrayList<>();
        for (ReparatieHandeling handeling : repository.findAll()) {
            dtos.add(toDto(handeling));
        }
        return dtos;
    }

    public ReparatieHandelingResponseDto getReparatieHandelingById(Long id) {
        return toDto(findHandeling(id));
    }

    public ReparatieHandelingResponseDto createReparatieHandeling(ReparatieHandelingRequestDto dto) {
        ReparatieHandeling handeling = new ReparatieHandeling();
        handeling.setNaam(dto.naam);
        handeling.setPrijsExclBtw(dto.prijsExclBtw);
        return toDto(repository.save(handeling));
    }

    public ReparatieHandelingResponseDto updateReparatieHandeling(Long id, ReparatieHandelingRequestDto dto) {
        ReparatieHandeling handeling = findHandeling(id);
        handeling.setNaam(dto.naam);
        handeling.setPrijsExclBtw(dto.prijsExclBtw);
        return toDto(repository.save(handeling));
    }

    public void deleteReparatieHandeling(Long id) {
        if (!repository.existsById(id)) {
            throw new RecordNotFoundException("Reparatiehandeling met id " + id + " niet gevonden");
        }
        repository.deleteById(id);
    }

    private ReparatieHandeling findHandeling(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Reparatiehandeling met id " + id + " niet gevonden"));
    }

    private ReparatieHandelingResponseDto toDto(ReparatieHandeling handeling) {
        ReparatieHandelingResponseDto dto = new ReparatieHandelingResponseDto();
        dto.id = handeling.getId();
        dto.naam = handeling.getNaam();
        dto.prijsExclBtw = handeling.getPrijsExclBtw();
        return dto;
    }
}
