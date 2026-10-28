package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;

public class OverigeHandelingResponseDto {

    private Long id;
    private String omschrijving;
    private BigDecimal prijsExclBtw;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public BigDecimal getPrijsExclBtw() {
        return prijsExclBtw;
    }

    public void setPrijsExclBtw(BigDecimal prijsExclBtw) {
        this.prijsExclBtw = prijsExclBtw;
    }
}
