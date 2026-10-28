package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class KlantRequestDto {

    @NotBlank(message = "naam is verplicht")
    private String naam;
    @NotBlank(message = "telefoonnummer is verplicht")
    private String telefoonnummer;
    @Email
    private String emailadres;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }
}
