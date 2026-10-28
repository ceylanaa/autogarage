package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;

public class ReparatieRegelResponseDto {

    private Long id;
    private Long reparatieHandelingId;
    private String naam;
    private BigDecimal prijsExclBtw;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReparatieHandelingId() {
        return reparatieHandelingId;
    }

    public void setReparatieHandelingId(Long reparatieHandelingId) {
        this.reparatieHandelingId = reparatieHandelingId;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public BigDecimal getPrijsExclBtw() {
        return prijsExclBtw;
    }

    public void setPrijsExclBtw(BigDecimal prijsExclBtw) {
        this.prijsExclBtw = prijsExclBtw;
    }
}
