package nl.novi.autogarage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

// vaste handelingen die in het systeem staan (bijv. "remmen vervangen")
@Entity
@Table(name = "reparatiehandelingen")
@Getter
@Setter
@NoArgsConstructor
public class ReparatieHandeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naam;
    private BigDecimal prijsExclBtw;
}
