package nl.novi.autogarage.repositories;

import nl.novi.autogarage.models.Reparatie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReparatieRepository extends JpaRepository<Reparatie, Long> {

    boolean existsByKeuringId(Long keuringId);
}
