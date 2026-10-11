package nl.novi.autogarage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// koppeltabel tussen reparatie en een vaste reparatiehandeling
@Entity
@Table(name = "reparatie_regels")
@Getter
@Setter
@NoArgsConstructor
public class ReparatieRegel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reparatie_id")
    private Reparatie reparatie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reparatiehandeling_id")
    private ReparatieHandeling reparatieHandeling;
}
