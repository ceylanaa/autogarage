package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class KlantRequestDto {

    @NotBlank(message = "naam is verplicht")
    public String naam;
    @NotBlank(message = "telefoonnummer is verplicht")
    public String telefoonnummer;
    @Email
    public String emailadres;
}
