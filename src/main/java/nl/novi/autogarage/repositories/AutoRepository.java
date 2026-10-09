package nl.novi.autogarage.repositories;

import nl.novi.autogarage.models.Auto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoRepository extends JpaRepository<Auto, Long> {

    boolean existsByKenteken(String kenteken);
}
