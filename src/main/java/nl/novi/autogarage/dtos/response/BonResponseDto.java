package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BonResponseDto {

    private Long reparatieId;
    private String kenteken;
    private String klantNaam;
    private BigDecimal keuringBedragExcl;
    private BigDecimal keuringBedragIncl;
    public List<BonRegelDto> handelingen = new ArrayList<>();
    public List<BonRegelDto> overigeHandelingen = new ArrayList<>();
    public List<BonOnderdeelRegelDto> onderdelen = new ArrayList<>();
    private BigDecimal totalExcl;
    private BigDecimal btwBedrag;
    private BigDecimal totalIncl;
    private boolean betaald;

    public Long getReparatieId() {
        return reparatieId;
    }

    public void setReparatieId(Long reparatieId) {
        this.reparatieId = reparatieId;
    }

    public String getKenteken() {
        return kenteken;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public String getKlantNaam() {
        return klantNaam;
    }

    public void setKlantNaam(String klantNaam) {
        this.klantNaam = klantNaam;
    }

    public BigDecimal getKeuringBedragExcl() {
        return keuringBedragExcl;
    }

    public void setKeuringBedragExcl(BigDecimal keuringBedragExcl) {
        this.keuringBedragExcl = keuringBedragExcl;
    }

    public BigDecimal getKeuringBedragIncl() {
        return keuringBedragIncl;
    }

    public void setKeuringBedragIncl(BigDecimal keuringBedragIncl) {
        this.keuringBedragIncl = keuringBedragIncl;
    }

    public BigDecimal getTotalExcl() {
        return totalExcl;
    }

    public void setTotalExcl(BigDecimal totalExcl) {
        this.totalExcl = totalExcl;
    }

    public BigDecimal getBtwBedrag() {
        return btwBedrag;
    }

    public void setBtwBedrag(BigDecimal btwBedrag) {
        this.btwBedrag = btwBedrag;
    }

    public BigDecimal getTotalIncl() {
        return totalIncl;
    }

    public void setTotalIncl(BigDecimal totalIncl) {
        this.totalIncl = totalIncl;
    }

    public boolean getBetaald() {
        return betaald;
    }

    public void setBetaald(boolean betaald) {
        this.betaald = betaald;
    }
}
