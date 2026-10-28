package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.KeuringRequestDto;
import nl.novi.autogarage.dtos.request.KeuringStatusRequestDto;
import nl.novi.autogarage.dtos.response.KeuringResponseDto;
import nl.novi.autogarage.exceptions.BadRequestException;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.Auto;
import nl.novi.autogarage.models.Keuring;
import nl.novi.autogarage.models.KeuringStatus;
import nl.novi.autogarage.repositories.AutoRepository;
import nl.novi.autogarage.repositories.KeuringRepository;
import nl.novi.autogarage.mappers.KeuringMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeuringService {

    private final KeuringRepository keuringRepository;
    private final AutoRepository autoRepository;
    private final KeuringMapper keuringMapper;

    public KeuringService(KeuringRepository keuringRepository, AutoRepository autoRepository, KeuringMapper keuringMapper) {
        this.keuringRepository = keuringRepository;
        this.autoRepository = autoRepository;
        this.keuringMapper = keuringMapper;
    }

    public List<KeuringResponseDto> getAllKeuringen() {
        List<KeuringResponseDto> dtos = new ArrayList<>();
        for (Keuring keuring : keuringRepository.findAll()) {
            dtos.add(keuringMapper.toResponseDto(keuring));
        }
        return dtos;
    }

    public KeuringResponseDto getKeuringById(Long id) {
        return keuringMapper.toResponseDto(findKeuring(id));
    }

    public KeuringResponseDto createKeuring(KeuringRequestDto dto) {
        Auto auto = autoRepository.findById(dto.getAutoId())
                .orElseThrow(() -> new RecordNotFoundException("Auto met id " + dto.getAutoId() + " niet gevonden"));
        Keuring keuring = new Keuring();
        keuring.setAuto(auto);
        keuring.setKeuringsDatum(dto.getKeuringsDatum());
        keuring.setStatus(KeuringStatus.GEPLAND);
        return keuringMapper.toResponseDto(keuringRepository.save(keuring));
    }

    public KeuringResponseDto updateKeuringStatus(Long id, KeuringStatusRequestDto dto) {
        Keuring keuring = findKeuring(id);
        try {
            keuring.setStatus(KeuringStatus.valueOf(dto.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Ongeldige status: " + dto.getStatus());
        }
        if (dto.getTekortkomingen() != null) {
            keuring.setTekortkomingen(dto.getTekortkomingen());
        }
        return keuringMapper.toResponseDto(keuringRepository.save(keuring));
    }

    public void deleteKeuring(Long id) {
        if (!keuringRepository.existsById(id)) {
            throw new RecordNotFoundException("Keuring met id " + id + " niet gevonden");
        }
        keuringRepository.deleteById(id);
    }

    private Keuring findKeuring(Long id) {
        return keuringRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Keuring met id " + id + " niet gevonden"));
    }
}
