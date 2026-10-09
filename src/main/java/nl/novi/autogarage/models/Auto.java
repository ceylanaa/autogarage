package nl.novi.autogarage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "autos")
@Getter
@Setter
@NoArgsConstructor
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String kenteken;

    private String merk;
    private String model;

    // alleen de bestandsnaam, het bestand zelf staat in de uploads map
    private String autopapierNaam;

    @ManyToOne(optional = false)
    @JoinColumn(name = "klant_id")
    private Klant klant;
}
