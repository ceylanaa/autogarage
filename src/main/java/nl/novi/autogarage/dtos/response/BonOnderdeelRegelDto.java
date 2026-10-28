package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;

public class BonOnderdeelRegelDto {

    private String naam;
    private Integer aantal;
    private BigDecimal prijsExcl;
    private BigDecimal prijsIncl;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Integer getAantal() {
        return aantal;
    }

    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }

    public BigDecimal getPrijsExcl() {
        return prijsExcl;
    }

    public void setPrijsExcl(BigDecimal prijsExcl) {
        this.prijsExcl = prijsExcl;
    }

    public BigDecimal getPrijsIncl() {
        return prijsIncl;
    }

    public void setPrijsIncl(BigDecimal prijsIncl) {
        this.prijsIncl = prijsIncl;
    }
}
