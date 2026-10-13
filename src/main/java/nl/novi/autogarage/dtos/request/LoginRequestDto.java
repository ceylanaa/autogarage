package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class LoginRequestDto {

    @NotBlank
    public String gebruikersnaam;
    @NotBlank
    public String wachtwoord;
}
