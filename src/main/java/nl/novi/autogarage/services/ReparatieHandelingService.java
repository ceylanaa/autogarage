package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.ReparatieHandelingRequestDto;
import nl.novi.autogarage.dtos.response.ReparatieHandelingResponseDto;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.ReparatieHandeling;
import nl.novi.autogarage.repositories.ReparatieHandelingRepository;
import nl.novi.autogarage.mappers.ReparatieHandelingMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReparatieHandelingService {

    private final ReparatieHandelingRepository repository;
    private final ReparatieHandelingMapper reparatieHandelingMapper;

    public ReparatieHandelingService(ReparatieHandelingRepository repository, ReparatieHandelingMapper reparatieHandelingMapper) {
        this.repository = repository;
        this.reparatieHandelingMapper = reparatieHandelingMapper;
    }

    public List<ReparatieHandelingResponseDto> getAllReparatieHandelingen() {
        List<ReparatieHandelingResponseDto> dtos = new ArrayList<>();
        for (ReparatieHandeling handeling : repository.findAll()) {
            dtos.add(reparatieHandelingMapper.toResponseDto(handeling));
        }
        return dtos;
    }

    public ReparatieHandelingResponseDto getReparatieHandelingById(Long id) {
        return reparatieHandelingMapper.toResponseDto(findHandeling(id));
    }

    public ReparatieHandelingResponseDto createReparatieHandeling(ReparatieHandelingRequestDto dto) {
        ReparatieHandeling handeling = new ReparatieHandeling();
        reparatieHandelingMapper.vulEntity(handeling, dto);
        return reparatieHandelingMapper.toResponseDto(repository.save(handeling));
    }

    public ReparatieHandelingResponseDto updateReparatieHandeling(Long id, ReparatieHandelingRequestDto dto) {
        ReparatieHandeling handeling = findHandeling(id);
        reparatieHandelingMapper.vulEntity(handeling, dto);
        return reparatieHandelingMapper.toResponseDto(repository.save(handeling));
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
}
