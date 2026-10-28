package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ReparatieRequestDto {

    @NotNull
    private Long keuringId;
    private LocalDate reparatieDatum;

    public Long getKeuringId() {
        return keuringId;
    }

    public void setKeuringId(Long keuringId) {
        this.keuringId = keuringId;
    }

    public LocalDate getReparatieDatum() {
        return reparatieDatum;
    }

    public void setReparatieDatum(LocalDate reparatieDatum) {
        this.reparatieDatum = reparatieDatum;
    }
}
