package nl.novi.autogarage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// koppeltabel tussen reparatie en onderdeel, met het aantal gebruikte stuks
@Entity
@Table(name = "reparatie_onderdelen")
@Getter
@Setter
@NoArgsConstructor
public class ReparatieOnderdeel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reparatie_id")
    private Reparatie reparatie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "onderdeel_id")
    private Onderdeel onderdeel;

    private Integer aantal;
}
