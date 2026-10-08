package nl.novi.autogarage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rollen")
@Getter
@Setter
@NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // KASSAMEDEWERKER, MONTEUR of BACKOFFICE
    @Column(nullable = false, unique = true)
    private String naam;

    public Rol(String naam) {
        this.naam = naam;
    }
}
