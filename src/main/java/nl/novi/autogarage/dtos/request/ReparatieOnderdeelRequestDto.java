package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class ReparatieOnderdeelRequestDto {

    @NotNull
    public Long onderdeelId;
    @NotNull
    @Positive
    public Integer aantal;
}
