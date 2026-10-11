package nl.novi.autogarage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

// handeling die niet in het systeem staat, de monteur vult zelf de omschrijving en prijs in
@Entity
@Table(name = "overige_handelingen")
@Getter
@Setter
@NoArgsConstructor
public class OverigeHandeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reparatie_id")
    private Reparatie reparatie;

    private String omschrijving;
    private BigDecimal prijsExclBtw;
}
