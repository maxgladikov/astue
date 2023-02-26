package astue.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import astue.model.Plant;


public interface PlantRepository extends JpaRepository<Plant,Long> {
	Optional<Plant> findByName(String name);
}












