package nl.novi.autogarage.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "onderdelen")
public class Onderdeel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naam;
    private BigDecimal prijsExclBtw;
    private Integer voorraad;

    public Onderdeel() {
    }

    public Onderdeel(String naam, BigDecimal prijsExclBtw, Integer voorraad) {
        this.naam = naam;
        this.prijsExclBtw = prijsExclBtw;
        this.voorraad = voorraad;
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

    public BigDecimal getPrijsExclBtw() {
        return prijsExclBtw;
    }

    public void setPrijsExclBtw(BigDecimal prijsExclBtw) {
        this.prijsExclBtw = prijsExclBtw;
    }

    public Integer getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(Integer voorraad) {
        this.voorraad = voorraad;
    }
}
