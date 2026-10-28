package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class KeuringRequestDto {

    @NotNull
    private Long autoId;
    @NotNull
    private LocalDate keuringsDatum;

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public LocalDate getKeuringsDatum() {
        return keuringsDatum;
    }

    public void setKeuringsDatum(LocalDate keuringsDatum) {
        this.keuringsDatum = keuringsDatum;
    }
}
