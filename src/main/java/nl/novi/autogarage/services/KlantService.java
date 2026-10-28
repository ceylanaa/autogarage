package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.KlantRequestDto;
import nl.novi.autogarage.dtos.response.KlantResponseDto;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.Klant;
import nl.novi.autogarage.repositories.KlantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KlantService {

    private final KlantRepository klantRepository;

    public KlantService(KlantRepository klantRepository) {
        this.klantRepository = klantRepository;
    }

    public List<KlantResponseDto> getAllKlanten() {
        List<KlantResponseDto> dtos = new ArrayList<>();
        for (Klant klant : klantRepository.findAll()) {
            dtos.add(klantToResponseDto(klant));
        }
        return dtos;
    }

    public KlantResponseDto getKlantById(Long id) {
        return klantToResponseDto(findKlant(id));
    }

    public KlantResponseDto createKlant(KlantRequestDto dto) {
        Klant klant = requestDtoToKlant(dto);
        return klantToResponseDto(klantRepository.save(klant));
    }

    public KlantResponseDto updateKlant(Long id, KlantRequestDto dto) {
        Klant klant = findKlant(id);
        klant.setNaam(dto.getNaam());
        klant.setTelefoonnummer(dto.getTelefoonnummer());
        klant.setEmailadres(dto.getEmailadres());
        return klantToResponseDto(klantRepository.save(klant));
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

    private KlantResponseDto klantToResponseDto(Klant klant) {
        KlantResponseDto dto = new KlantResponseDto();
        dto.setId(klant.getId());
        dto.setNaam(klant.getNaam());
        dto.setTelefoonnummer(klant.getTelefoonnummer());
        dto.setEmailadres(klant.getEmailadres());
        return dto;
    }

    private Klant requestDtoToKlant(KlantRequestDto dto) {
        Klant klant = new Klant();
        klant.setNaam(dto.getNaam());
        klant.setTelefoonnummer(dto.getTelefoonnummer());
        klant.setEmailadres(dto.getEmailadres());
        return klant;
    }
}
