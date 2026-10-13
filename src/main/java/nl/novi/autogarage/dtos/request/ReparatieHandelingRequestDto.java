package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ReparatieHandelingRequestDto {

    @NotBlank
    public String naam;
    @NotNull
    @PositiveOrZero
    public BigDecimal prijsExclBtw;
}
