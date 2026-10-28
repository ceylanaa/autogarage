package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class ReparatieRegelRequestDto {

    @NotNull
    private Long reparatieHandelingId;

    public Long getReparatieHandelingId() {
        return reparatieHandelingId;
    }

    public void setReparatieHandelingId(Long reparatieHandelingId) {
        this.reparatieHandelingId = reparatieHandelingId;
    }
}
