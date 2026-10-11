package nl.novi.autogarage.repositories;

import nl.novi.autogarage.models.Onderdeel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnderdeelRepository extends JpaRepository<Onderdeel, Long> {
}
