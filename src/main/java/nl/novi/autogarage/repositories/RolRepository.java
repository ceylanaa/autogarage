package nl.novi.autogarage.repositories;

import nl.novi.autogarage.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNaam(String naam);
}
