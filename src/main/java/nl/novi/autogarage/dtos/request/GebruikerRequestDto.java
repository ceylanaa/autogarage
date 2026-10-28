package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class GebruikerRequestDto {

    @NotBlank
    private String gebruikersnaam;
    @NotBlank
    @Size(min = 6, message = "wachtwoord moet minimaal 6 tekens zijn")
    private String wachtwoord;
    @NotBlank
    private String rolNaam;

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

    public String getRolNaam() {
        return rolNaam;
    }

    public void setRolNaam(String rolNaam) {
        this.rolNaam = rolNaam;
    }
}
