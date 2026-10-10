package nl.novi.autogarage.repositories;

import nl.novi.autogarage.models.Keuring;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeuringRepository extends JpaRepository<Keuring, Long> {

    List<Keuring> findByAutoId(Long autoId);
}
