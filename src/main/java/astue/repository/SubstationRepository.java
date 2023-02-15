package astue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import astue.model.Substation;


public interface SubstationRepository extends JpaRepository<Substation,Long> {
    public Optional<Substation> findByName(String name);
}












