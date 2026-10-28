package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class ReparatieOnderdeelRequestDto {

    @NotNull
    private Long onderdeelId;
    @NotNull
    @Positive
    private Integer aantal;

    public Long getOnderdeelId() {
        return onderdeelId;
    }

    public void setOnderdeelId(Long onderdeelId) {
        this.onderdeelId = onderdeelId;
    }

    public Integer getAantal() {
        return aantal;
    }

    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }
}
