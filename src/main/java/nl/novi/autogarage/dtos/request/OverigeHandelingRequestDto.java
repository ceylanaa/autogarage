package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class OverigeHandelingRequestDto {

    @NotBlank
    public String omschrijving;
    @NotNull
    @PositiveOrZero
    public BigDecimal prijsExclBtw;
}
