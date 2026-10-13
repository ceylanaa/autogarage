package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class ReparatieRegelRequestDto {

    @NotNull
    public Long reparatieHandelingId;
}
