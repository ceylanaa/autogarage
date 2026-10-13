package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class OnderdeelRequestDto {

    @NotBlank(message = "naam is verplicht")
    public String naam;
    @NotNull
    @PositiveOrZero
    public BigDecimal prijsExclBtw;
    @NotNull
    @PositiveOrZero
    public Integer voorraad;
}
