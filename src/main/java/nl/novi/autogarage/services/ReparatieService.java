package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.*;
import nl.novi.autogarage.dtos.response.*;
import nl.novi.autogarage.exceptions.BadRequestException;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.*;
import nl.novi.autogarage.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReparatieService {

    private final ReparatieRepository reparatieRepository;
    private final KeuringRepository keuringRepository;
    private final OnderdeelRepository onderdeelRepository;
    private final ReparatieHandelingRepository reparatieHandelingRepository;

    public ReparatieService(ReparatieRepository reparatieRepository, KeuringRepository keuringRepository,
                            OnderdeelRepository onderdeelRepository, ReparatieHandelingRepository reparatieHandelingRepository) {
        this.reparatieRepository = reparatieRepository;
        this.keuringRepository = keuringRepository;
        this.onderdeelRepository = onderdeelRepository;
        this.reparatieHandelingRepository = reparatieHandelingRepository;
    }

    public List<ReparatieResponseDto> getAllReparaties() {
        List<ReparatieResponseDto> dtos = new ArrayList<>();
        for (Reparatie reparatie : reparatieRepository.findAll()) {
            dtos.add(reparatieToResponseDto(reparatie));
        }
        return dtos;
    }

    public ReparatieResponseDto getReparatieById(Long id) {
        return reparatieToResponseDto(findReparatie(id));
    }

    public ReparatieResponseDto createReparatie(ReparatieRequestDto dto) {
        Keuring keuring = keuringRepository.findById(dto.keuringId)
                .orElseThrow(() -> new RecordNotFoundException("Keuring met id " + dto.keuringId + " niet gevonden"));
        if (reparatieRepository.existsByKeuringId(dto.keuringId)) {
            throw new BadRequestException("Er bestaat al een reparatie voor keuring " + dto.keuringId);
        }
        Reparatie reparatie = new Reparatie();
        reparatie.setKeuring(keuring);
        reparatie.setReparatieDatum(dto.reparatieDatum);
        reparatie.setStatus(ReparatieStatus.GEPLAND);
        reparatie.setBetaald(false);
        return reparatieToResponseDto(reparatieRepository.save(reparatie));
    }

    public ReparatieResponseDto updateReparatieStatus(Long id, ReparatieStatusRequestDto dto) {
        Reparatie reparatie = findReparatie(id);
        try {
            reparatie.setStatus(ReparatieStatus.valueOf(dto.status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Ongeldige status: " + dto.status);
        }
        return reparatieToResponseDto(reparatieRepository.save(reparatie));
    }

    public ReparatieOnderdeelResponseDto addOnderdeel(Long reparatieId, ReparatieOnderdeelRequestDto dto) {
        Reparatie reparatie = findReparatie(reparatieId);
        Onderdeel onderdeel = onderdeelRepository.findById(dto.onderdeelId)
                .orElseThrow(() -> new RecordNotFoundException("Onderdeel met id " + dto.onderdeelId + " niet gevonden"));

        // voorraad controleren en bijwerken
        if (onderdeel.getVoorraad() < dto.aantal) {
            throw new BadRequestException("Niet genoeg voorraad van " + onderdeel.getNaam());
        }
        onderdeel.setVoorraad(onderdeel.getVoorraad() - dto.aantal);

        ReparatieOnderdeel ro = new ReparatieOnderdeel();
        ro.setReparatie(reparatie);
        ro.setOnderdeel(onderdeel);
        ro.setAantal(dto.aantal);
        reparatie.getOnderdelen().add(ro);
        reparatieRepository.saveAndFlush(reparatie);

        // de laatst toegevoegde heeft nu een id
        ReparatieOnderdeel opgeslagen = reparatie.getOnderdelen().get(reparatie.getOnderdelen().size() - 1);
        return onderdeelToDto(opgeslagen);
    }

    public void removeOnderdeel(Long reparatieId, Long reparatieOnderdeelId) {
        Reparatie reparatie = findReparatie(reparatieId);
        ReparatieOnderdeel gevonden = null;
        for (ReparatieOnderdeel ro : reparatie.getOnderdelen()) {
            if (ro.getId().equals(reparatieOnderdeelId)) {
                gevonden = ro;
            }
        }
        if (gevonden == null) {
            throw new RecordNotFoundException("Onderdeel " + reparatieOnderdeelId + " zit niet in deze reparatie");
        }
        // voorraad weer terugzetten
        Onderdeel onderdeel = gevonden.getOnderdeel();
        onderdeel.setVoorraad(onderdeel.getVoorraad() + gevonden.getAantal());
        reparatie.getOnderdelen().remove(gevonden);
        reparatieRepository.save(reparatie);
    }

    public ReparatieRegelResponseDto addHandeling(Long reparatieId, ReparatieRegelRequestDto dto) {
        Reparatie reparatie = findReparatie(reparatieId);
        ReparatieHandeling handeling = reparatieHandelingRepository.findById(dto.reparatieHandelingId)
                .orElseThrow(() -> new RecordNotFoundException("Reparatiehandeling met id " + dto.reparatieHandelingId + " niet gevonden"));

        ReparatieRegel regel = new ReparatieRegel();
        regel.setReparatie(reparatie);
        regel.setReparatieHandeling(handeling);
        reparatie.getHandelingen().add(regel);
        reparatieRepository.saveAndFlush(reparatie);

        return regelToDto(reparatie.getHandelingen().get(reparatie.getHandelingen().size() - 1));
    }

    public void removeHandeling(Long reparatieId, Long reparatieRegelId) {
        Reparatie reparatie = findReparatie(reparatieId);
        boolean verwijderd = reparatie.getHandelingen().removeIf(r -> r.getId().equals(reparatieRegelId));
        if (!verwijderd) {
            throw new RecordNotFoundException("Handeling " + reparatieRegelId + " zit niet in deze reparatie");
        }
        reparatieRepository.save(reparatie);
    }

    public OverigeHandelingResponseDto addOverigeHandeling(Long reparatieId, OverigeHandelingRequestDto dto) {
        Reparatie reparatie = findReparatie(reparatieId);
        OverigeHandeling overig = new OverigeHandeling();
        overig.setReparatie(reparatie);
        overig.setOmschrijving(dto.omschrijving);
        overig.setPrijsExclBtw(dto.prijsExclBtw);
        reparatie.getOverigeHandelingen().add(overig);
        reparatieRepository.saveAndFlush(reparatie);

        return overigToDto(reparatie.getOverigeHandelingen().get(reparatie.getOverigeHandelingen().size() - 1));
    }

    public void removeOverigeHandeling(Long reparatieId, Long overigeHandelingId) {
        Reparatie reparatie = findReparatie(reparatieId);
        boolean verwijderd = reparatie.getOverigeHandelingen().removeIf(o -> o.getId().equals(overigeHandelingId));
        if (!verwijderd) {
            throw new RecordNotFoundException("Overige handeling " + overigeHandelingId + " zit niet in deze reparatie");
        }
        reparatieRepository.save(reparatie);
    }

    public ReparatieResponseDto markeerBetaald(Long id) {
        Reparatie reparatie = findReparatie(id);
        reparatie.setBetaald(true);
        return reparatieToResponseDto(reparatieRepository.save(reparatie));
    }

    private Reparatie findReparatie(Long id) {
        return reparatieRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Reparatie met id " + id + " niet gevonden"));
    }

    private ReparatieResponseDto reparatieToResponseDto(Reparatie reparatie) {
        ReparatieResponseDto dto = new ReparatieResponseDto();
        dto.id = reparatie.getId();
        dto.keuringId = reparatie.getKeuring().getId();
        dto.reparatieDatum = reparatie.getReparatieDatum();
        dto.status = reparatie.getStatus().name();
        dto.betaald = reparatie.isBetaald();
        for (ReparatieOnderdeel ro : reparatie.getOnderdelen()) {
            dto.onderdelen.add(onderdeelToDto(ro));
        }
        for (ReparatieRegel regel : reparatie.getHandelingen()) {
            dto.handelingen.add(regelToDto(regel));
        }
        for (OverigeHandeling overig : reparatie.getOverigeHandelingen()) {
            dto.overigeHandelingen.add(overigToDto(overig));
        }
        return dto;
    }

    private ReparatieOnderdeelResponseDto onderdeelToDto(ReparatieOnderdeel ro) {
        ReparatieOnderdeelResponseDto dto = new ReparatieOnderdeelResponseDto();
        dto.id = ro.getId();
        dto.onderdeelId = ro.getOnderdeel().getId();
        dto.naam = ro.getOnderdeel().getNaam();
        dto.prijsExclBtw = ro.getOnderdeel().getPrijsExclBtw();
        dto.aantal = ro.getAantal();
        return dto;
    }

    private ReparatieRegelResponseDto regelToDto(ReparatieRegel regel) {
        ReparatieRegelResponseDto dto = new ReparatieRegelResponseDto();
        dto.id = regel.getId();
        dto.reparatieHandelingId = regel.getReparatieHandeling().getId();
        dto.naam = regel.getReparatieHandeling().getNaam();
        dto.prijsExclBtw = regel.getReparatieHandeling().getPrijsExclBtw();
        return dto;
    }

    private OverigeHandelingResponseDto overigToDto(OverigeHandeling overig) {
        OverigeHandelingResponseDto dto = new OverigeHandelingResponseDto();
        dto.id = overig.getId();
        dto.omschrijving = overig.getOmschrijving();
        dto.prijsExclBtw = overig.getPrijsExclBtw();
        return dto;
    }
}
