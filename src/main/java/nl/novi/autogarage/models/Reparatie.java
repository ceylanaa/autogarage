package nl.novi.autogarage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reparaties")
@Getter
@Setter
@NoArgsConstructor
public class Reparatie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // bij elke keuring hoort maximaal 1 reparatie
    @OneToOne(optional = false)
    @JoinColumn(name = "keuring_id", unique = true)
    private Keuring keuring;

    private LocalDate reparatieDatum;

    @Enumerated(EnumType.STRING)
    private ReparatieStatus status;

    private boolean betaald = false;

    @OneToMany(mappedBy = "reparatie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReparatieOnderdeel> onderdelen = new ArrayList<>();

    @OneToMany(mappedBy = "reparatie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReparatieRegel> handelingen = new ArrayList<>();

    @OneToMany(mappedBy = "reparatie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OverigeHandeling> overigeHandelingen = new ArrayList<>();
}
