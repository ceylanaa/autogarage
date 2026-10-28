package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;

public class BonRegelDto {

    private String omschrijving;
    private BigDecimal prijsExcl;
    private BigDecimal prijsIncl;

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
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
