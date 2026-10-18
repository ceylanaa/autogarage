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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeuringService {

    private final KeuringRepository keuringRepository;
    private final AutoRepository autoRepository;

    public KeuringService(KeuringRepository keuringRepository, AutoRepository autoRepository) {
        this.keuringRepository = keuringRepository;
        this.autoRepository = autoRepository;
    }

    public List<KeuringResponseDto> getAllKeuringen() {
        List<KeuringResponseDto> dtos = new ArrayList<>();
        for (Keuring keuring : keuringRepository.findAll()) {
            dtos.add(keuringToResponseDto(keuring));
        }
        return dtos;
    }

    public KeuringResponseDto getKeuringById(Long id) {
        return keuringToResponseDto(findKeuring(id));
    }

    public KeuringResponseDto createKeuring(KeuringRequestDto dto) {
        Auto auto = autoRepository.findById(dto.autoId)
                .orElseThrow(() -> new RecordNotFoundException("Auto met id " + dto.autoId + " niet gevonden"));
        Keuring keuring = new Keuring();
        keuring.setAuto(auto);
        keuring.setKeuringsDatum(dto.keuringsDatum);
        keuring.setStatus(KeuringStatus.GEPLAND);
        return keuringToResponseDto(keuringRepository.save(keuring));
    }

    public KeuringResponseDto updateKeuringStatus(Long id, KeuringStatusRequestDto dto) {
        Keuring keuring = findKeuring(id);
        try {
            keuring.setStatus(KeuringStatus.valueOf(dto.status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Ongeldige status: " + dto.status);
        }
        if (dto.tekortkomingen != null) {
            keuring.setTekortkomingen(dto.tekortkomingen);
        }
        return keuringToResponseDto(keuringRepository.save(keuring));
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

    private KeuringResponseDto keuringToResponseDto(Keuring keuring) {
        KeuringResponseDto dto = new KeuringResponseDto();
        dto.id = keuring.getId();
        dto.autoId = keuring.getAuto().getId();
        dto.kenteken = keuring.getAuto().getKenteken();
        dto.keuringsDatum = keuring.getKeuringsDatum();
        dto.tekortkomingen = keuring.getTekortkomingen();
        dto.status = keuring.getStatus().name();
        dto.keuringsBedrag = Keuring.KEURINGS_BEDRAG;
        return dto;
    }
}
