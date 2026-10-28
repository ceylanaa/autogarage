package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.*;
import nl.novi.autogarage.dtos.response.*;
import nl.novi.autogarage.exceptions.BadRequestException;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.*;
import nl.novi.autogarage.repositories.*;
import nl.novi.autogarage.mappers.ReparatieMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReparatieService {

    // btw percentage is 21%
    public static final BigDecimal BTW = new BigDecimal("0.21");

    private final ReparatieRepository reparatieRepository;
    private final KeuringRepository keuringRepository;
    private final OnderdeelRepository onderdeelRepository;
    private final ReparatieHandelingRepository reparatieHandelingRepository;
    private final ReparatieMapper reparatieMapper;

    public ReparatieService(ReparatieRepository reparatieRepository, KeuringRepository keuringRepository,
                            OnderdeelRepository onderdeelRepository, ReparatieHandelingRepository reparatieHandelingRepository, ReparatieMapper reparatieMapper) {
        this.reparatieRepository = reparatieRepository;
        this.keuringRepository = keuringRepository;
        this.onderdeelRepository = onderdeelRepository;
        this.reparatieHandelingRepository = reparatieHandelingRepository;
        this.reparatieMapper = reparatieMapper;
    }

    public List<ReparatieResponseDto> getAllReparaties() {
        List<ReparatieResponseDto> dtos = new ArrayList<>();
        for (Reparatie reparatie : reparatieRepository.findAll()) {
            dtos.add(reparatieMapper.toResponseDto(reparatie));
        }
        return dtos;
    }

    public ReparatieResponseDto getReparatieById(Long id) {
        return reparatieMapper.toResponseDto(findReparatie(id));
    }

    public ReparatieResponseDto createReparatie(ReparatieRequestDto dto) {
        Keuring keuring = keuringRepository.findById(dto.getKeuringId())
                .orElseThrow(() -> new RecordNotFoundException("Keuring met id " + dto.getKeuringId() + " niet gevonden"));
        if (reparatieRepository.existsByKeuringId(dto.getKeuringId())) {
            throw new BadRequestException("Er bestaat al een reparatie voor keuring " + dto.getKeuringId());
        }
        Reparatie reparatie = new Reparatie();
        reparatie.setKeuring(keuring);
        reparatie.setReparatieDatum(dto.getReparatieDatum());
        reparatie.setStatus(ReparatieStatus.GEPLAND);
        reparatie.setBetaald(false);
        return reparatieMapper.toResponseDto(reparatieRepository.save(reparatie));
    }

    public ReparatieResponseDto updateReparatieStatus(Long id, ReparatieStatusRequestDto dto) {
        Reparatie reparatie = findReparatie(id);
        try {
            reparatie.setStatus(ReparatieStatus.valueOf(dto.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Ongeldige status: " + dto.getStatus());
        }
        return reparatieMapper.toResponseDto(reparatieRepository.save(reparatie));
    }

    public ReparatieOnderdeelResponseDto addOnderdeel(Long reparatieId, ReparatieOnderdeelRequestDto dto) {
        Reparatie reparatie = findReparatie(reparatieId);
        Onderdeel onderdeel = onderdeelRepository.findById(dto.getOnderdeelId())
                .orElseThrow(() -> new RecordNotFoundException("Onderdeel met id " + dto.getOnderdeelId() + " niet gevonden"));

        // voorraad controleren en bijwerken
        if (onderdeel.getVoorraad() < dto.getAantal()) {
            throw new BadRequestException("Niet genoeg voorraad van " + onderdeel.getNaam());
        }
        onderdeel.setVoorraad(onderdeel.getVoorraad() - dto.getAantal());

        ReparatieOnderdeel ro = new ReparatieOnderdeel();
        ro.setReparatie(reparatie);
        ro.setOnderdeel(onderdeel);
        ro.setAantal(dto.getAantal());
        reparatie.getOnderdelen().add(ro);
        reparatieRepository.saveAndFlush(reparatie);

        // de laatst toegevoegde heeft nu een id
        ReparatieOnderdeel opgeslagen = reparatie.getOnderdelen().get(reparatie.getOnderdelen().size() - 1);
        return reparatieMapper.onderdeelToDto(opgeslagen);
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
        ReparatieHandeling handeling = reparatieHandelingRepository.findById(dto.getReparatieHandelingId())
                .orElseThrow(() -> new RecordNotFoundException("Reparatiehandeling met id " + dto.getReparatieHandelingId() + " niet gevonden"));

        ReparatieRegel regel = new ReparatieRegel();
        regel.setReparatie(reparatie);
        regel.setReparatieHandeling(handeling);
        reparatie.getHandelingen().add(regel);
        reparatieRepository.saveAndFlush(reparatie);

        return reparatieMapper.regelToDto(reparatie.getHandelingen().get(reparatie.getHandelingen().size() - 1));
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
        overig.setOmschrijving(dto.getOmschrijving());
        overig.setPrijsExclBtw(dto.getPrijsExclBtw());
        reparatie.getOverigeHandelingen().add(overig);
        reparatieRepository.saveAndFlush(reparatie);

        return reparatieMapper.overigToDto(reparatie.getOverigeHandelingen().get(reparatie.getOverigeHandelingen().size() - 1));
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
        return reparatieMapper.toResponseDto(reparatieRepository.save(reparatie));
    }

    // bon maken: keuring (45 euro) + handelingen + overige handelingen + onderdelen, alles met 21% btw
    @Transactional(readOnly = true)
    public BonResponseDto genereerBon(Long id) {
        Reparatie reparatie = findReparatie(id);
        BonResponseDto bon = new BonResponseDto();
        bon.setReparatieId(reparatie.getId());
        bon.setKenteken(reparatie.getKeuring().getAuto().getKenteken());
        bon.setKlantNaam(reparatie.getKeuring().getAuto().getKlant().getNaam());

        BigDecimal totaal = Keuring.KEURINGS_BEDRAG;
        bon.setKeuringBedragExcl(Keuring.KEURINGS_BEDRAG);
        bon.setKeuringBedragIncl(metBtw(Keuring.KEURINGS_BEDRAG));

        // als de klant niet akkoord is betaalt hij alleen de keuring
        if (reparatie.getStatus() != ReparatieStatus.NIET_UITVOEREN) {
            for (ReparatieRegel regel : reparatie.getHandelingen()) {
                BonRegelDto r = new BonRegelDto();
                r.setOmschrijving(regel.getReparatieHandeling().getNaam());
                r.setPrijsExcl(regel.getReparatieHandeling().getPrijsExclBtw());
                r.setPrijsIncl(metBtw(r.getPrijsExcl()));
                bon.getHandelingen().add(r);
                totaal = totaal.add(r.getPrijsExcl());
            }
            for (OverigeHandeling overig : reparatie.getOverigeHandelingen()) {
                BonRegelDto r = new BonRegelDto();
                r.setOmschrijving(overig.getOmschrijving());
                r.setPrijsExcl(overig.getPrijsExclBtw());
                r.setPrijsIncl(metBtw(r.getPrijsExcl()));
                bon.getOverigeHandelingen().add(r);
                totaal = totaal.add(r.getPrijsExcl());
            }
            for (ReparatieOnderdeel ro : reparatie.getOnderdelen()) {
                BonOnderdeelRegelDto r = new BonOnderdeelRegelDto();
                r.setNaam(ro.getOnderdeel().getNaam());
                r.setAantal(ro.getAantal());
                r.setPrijsExcl(ro.getOnderdeel().getPrijsExclBtw().multiply(BigDecimal.valueOf(ro.getAantal())));
                r.setPrijsIncl(metBtw(r.getPrijsExcl()));
                bon.getOnderdelen().add(r);
                totaal = totaal.add(r.getPrijsExcl());
            }
        }

        bon.setTotalExcl(totaal.setScale(2, RoundingMode.HALF_UP));
        bon.setTotalIncl(metBtw(totaal));
        bon.setBtwBedrag(bon.getTotalIncl().subtract(bon.getTotalExcl()));
        bon.setBetaald(reparatie.isBetaald());
        return bon;
    }

    private BigDecimal metBtw(BigDecimal bedrag) {
        return bedrag.multiply(BigDecimal.ONE.add(BTW)).setScale(2, RoundingMode.HALF_UP);
    }

    private Reparatie findReparatie(Long id) {
        return reparatieRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Reparatie met id " + id + " niet gevonden"));
    }
}
