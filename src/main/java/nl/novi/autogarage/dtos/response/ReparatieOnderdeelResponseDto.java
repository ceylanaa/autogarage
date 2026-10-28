package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;

public class ReparatieOnderdeelResponseDto {

    private Long id;
    private Long onderdeelId;
    private String naam;
    private BigDecimal prijsExclBtw;
    private Integer aantal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOnderdeelId() {
        return onderdeelId;
    }

    public void setOnderdeelId(Long onderdeelId) {
        this.onderdeelId = onderdeelId;
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

    public Integer getAantal() {
        return aantal;
    }

    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }
}
