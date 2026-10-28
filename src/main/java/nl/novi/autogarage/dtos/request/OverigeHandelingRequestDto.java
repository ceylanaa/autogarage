package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class OverigeHandelingRequestDto {

    @NotBlank
    private String omschrijving;
    @NotNull
    @PositiveOrZero
    private BigDecimal prijsExclBtw;

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
