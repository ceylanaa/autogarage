package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class GebruikerRequestDto {

    @NotBlank
    public String gebruikersnaam;
    @NotBlank
    @Size(min = 6, message = "wachtwoord moet minimaal 6 tekens zijn")
    public String wachtwoord;
    @NotBlank
    public String rolNaam;
}
