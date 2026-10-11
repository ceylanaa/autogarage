package nl.novi.autogarage.repositories;

import nl.novi.autogarage.models.ReparatieOnderdeel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReparatieOnderdeelRepository extends JpaRepository<ReparatieOnderdeel, Long> {
}
