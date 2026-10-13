package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class KeuringRequestDto {

    @NotNull
    public Long autoId;
    @NotNull
    public LocalDate keuringsDatum;
}
