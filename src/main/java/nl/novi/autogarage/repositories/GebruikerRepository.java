package nl.novi.autogarage.repositories;

import nl.novi.autogarage.models.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GebruikerRepository extends JpaRepository<Gebruiker, Long> {

    Optional<Gebruiker> findByGebruikersnaam(String gebruikersnaam);

    boolean existsByGebruikersnaam(String gebruikersnaam);
}
