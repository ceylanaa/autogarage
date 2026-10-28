package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class LoginRequestDto {

    @NotBlank
    private String gebruikersnaam;
    @NotBlank
    private String wachtwoord;

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}
