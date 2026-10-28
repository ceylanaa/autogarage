package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ReparatieHandelingRequestDto {

    @NotBlank
    private String naam;
    @NotNull
    @PositiveOrZero
    private BigDecimal prijsExclBtw;

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
