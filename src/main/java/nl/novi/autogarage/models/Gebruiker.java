package nl.novi.autogarage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gebruikers")
@Getter
@Setter
@NoArgsConstructor
public class Gebruiker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String gebruikersnaam;

    // wordt altijd met BCrypt opgeslagen, nooit plain text
    @Column(nullable = false)
    private String wachtwoord;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rol_id")
    private Rol rol;
}
