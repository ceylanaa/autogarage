package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.AutoRequestDto;
import nl.novi.autogarage.dtos.response.AutoResponseDto;
import nl.novi.autogarage.exceptions.BadRequestException;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.Auto;
import nl.novi.autogarage.models.Klant;
import nl.novi.autogarage.repositories.AutoRepository;
import nl.novi.autogarage.repositories.KlantRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutoService {

    private final AutoRepository autoRepository;
    private final KlantRepository klantRepository;
    private final FileStorageService fileStorageService;

    public AutoService(AutoRepository autoRepository, KlantRepository klantRepository, FileStorageService fileStorageService) {
        this.autoRepository = autoRepository;
        this.klantRepository = klantRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<AutoResponseDto> getAllAutos() {
        List<AutoResponseDto> dtos = new ArrayList<>();
        for (Auto auto : autoRepository.findAll()) {
            dtos.add(autoToResponseDto(auto));
        }
        return dtos;
    }

    public AutoResponseDto getAutoById(Long id) {
        return autoToResponseDto(findAuto(id));
    }

    public AutoResponseDto createAuto(AutoRequestDto dto) {
        if (autoRepository.existsByKenteken(dto.kenteken)) {
            throw new BadRequestException("Er bestaat al een auto met kenteken " + dto.kenteken);
        }
        Auto auto = new Auto();
        vulAuto(auto, dto);
        return autoToResponseDto(autoRepository.save(auto));
    }

    public AutoResponseDto updateAuto(Long id, AutoRequestDto dto) {
        Auto auto = findAuto(id);
        vulAuto(auto, dto);
        return autoToResponseDto(autoRepository.save(auto));
    }

    public void deleteAuto(Long id) {
        if (!autoRepository.existsById(id)) {
            throw new RecordNotFoundException("Auto met id " + id + " niet gevonden");
        }
        autoRepository.deleteById(id);
    }

    public void slaAutopapierOp(Long id, MultipartFile bestand) {
        Auto auto = findAuto(id);
        if (bestand == null || bestand.isEmpty()) {
            throw new BadRequestException("Geen bestand meegestuurd");
        }
        // alleen pdf toestaan
        String origineel = bestand.getOriginalFilename();
        if (origineel == null || !origineel.toLowerCase().endsWith(".pdf")) {
            throw new BadRequestException("Alleen PDF bestanden zijn toegestaan");
        }
        String naam = fileStorageService.slaBestandOp(bestand, "autopapier_" + auto.getId() + ".pdf");
        auto.setAutopapierNaam(naam);
        autoRepository.save(auto);
    }

    public Resource getAutopapier(Long id) {
        Auto auto = findAuto(id);
        if (auto.getAutopapierNaam() == null) {
            throw new RecordNotFoundException("Deze auto heeft nog geen autopapier");
        }
        return fileStorageService.laadBestand(auto.getAutopapierNaam());
    }

    private Auto findAuto(Long id) {
        return autoRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Auto met id " + id + " niet gevonden"));
    }

    private void vulAuto(Auto auto, AutoRequestDto dto) {
        Klant klant = klantRepository.findById(dto.klantId)
                .orElseThrow(() -> new RecordNotFoundException("Klant met id " + dto.klantId + " niet gevonden"));
        auto.setKenteken(dto.kenteken);
        auto.setMerk(dto.merk);
        auto.setModel(dto.model);
        auto.setKlant(klant);
    }

    private AutoResponseDto autoToResponseDto(Auto auto) {
        AutoResponseDto dto = new AutoResponseDto();
        dto.id = auto.getId();
        dto.kenteken = auto.getKenteken();
        dto.merk = auto.getMerk();
        dto.model = auto.getModel();
        dto.klantId = auto.getKlant().getId();
        dto.klantNaam = auto.getKlant().getNaam();
        dto.heeftAutopapier = auto.getAutopapierNaam() != null;
        return dto;
    }
}
