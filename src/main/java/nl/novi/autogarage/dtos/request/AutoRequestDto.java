package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class AutoRequestDto {

    @NotBlank(message = "kenteken is verplicht")
    public String kenteken;
    @NotBlank
    public String merk;
    public String model;
    @NotNull(message = "klantId is verplicht")
    public Long klantId;
}
