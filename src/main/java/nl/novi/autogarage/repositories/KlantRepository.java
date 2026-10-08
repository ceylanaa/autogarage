package nl.novi.autogarage.repositories;

import nl.novi.autogarage.models.Klant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KlantRepository extends JpaRepository<Klant, Long> {
}
