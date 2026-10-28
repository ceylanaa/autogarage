package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class AutoRequestDto {

    @NotBlank(message = "kenteken is verplicht")
    private String kenteken;
    @NotBlank
    private String merk;
    private String model;
    @NotNull(message = "klantId is verplicht")
    private Long klantId;

    public String getKenteken() {
        return kenteken;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getKlantId() {
        return klantId;
    }

    public void setKlantId(Long klantId) {
        this.klantId = klantId;
    }
}
