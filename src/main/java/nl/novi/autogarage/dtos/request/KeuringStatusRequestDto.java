package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class KeuringStatusRequestDto {

    @NotBlank
    private String status;
    private String tekortkomingen;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTekortkomingen() {
        return tekortkomingen;
    }

    public void setTekortkomingen(String tekortkomingen) {
        this.tekortkomingen = tekortkomingen;
    }
}
