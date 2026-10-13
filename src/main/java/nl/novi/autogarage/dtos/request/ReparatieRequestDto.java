package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ReparatieRequestDto {

    @NotNull
    public Long keuringId;
    public LocalDate reparatieDatum;
}
