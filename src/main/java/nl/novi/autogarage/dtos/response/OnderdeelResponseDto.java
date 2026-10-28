package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;

public class OnderdeelResponseDto {

    private Long id;
    private String naam;
    private BigDecimal prijsExclBtw;
    private Integer voorraad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(Integer voorraad) {
        this.voorraad = voorraad;
    }
}
