package nl.novi.autogarage.models;

import jakarta.persistence.*;

@Entity
@Table(name = "klanten")
public class Klant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naam;
    private String telefoonnummer;
    private String emailadres;

    public Klant() {
    }

    public Klant(String naam, String telefoonnummer, String emailadres) {
        this.naam = naam;
        this.telefoonnummer = telefoonnummer;
        this.emailadres = emailadres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
