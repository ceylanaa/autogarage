package nl.novi.autogarage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "keuringen")
@Getter
@Setter
@NoArgsConstructor
public class Keuring {

    // een keuring kost altijd 45 euro (excl. btw)
    public static final BigDecimal KEURINGS_BEDRAG = new BigDecimal("45.00");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "auto_id")
    private Auto auto;

    private LocalDate keuringsDatum;

    @Column(length = 1000)
    private String tekortkomingen;

    @Enumerated(EnumType.STRING)
    private KeuringStatus status;
}
