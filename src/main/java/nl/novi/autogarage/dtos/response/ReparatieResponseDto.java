package nl.novi.autogarage.dtos.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReparatieResponseDto {

    private Long id;
    private Long keuringId;
    private LocalDate reparatieDatum;
    private String status;
    private boolean betaald;
    public List<ReparatieOnderdeelResponseDto> onderdelen = new ArrayList<>();
    public List<ReparatieRegelResponseDto> handelingen = new ArrayList<>();
    public List<OverigeHandelingResponseDto> overigeHandelingen = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKeuringId() {
        return keuringId;
    }

    public void setKeuringId(Long keuringId) {
        this.keuringId = keuringId;
    }

    public LocalDate getReparatieDatum() {
        return reparatieDatum;
    }

    public void setReparatieDatum(LocalDate reparatieDatum) {
        this.reparatieDatum = reparatieDatum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getBetaald() {
        return betaald;
    }

    public void setBetaald(boolean betaald) {
        this.betaald = betaald;
    }
}
