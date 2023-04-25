package astue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import astue.model.Plant;


public interface PlantRepository extends JpaRepository<Plant,Long> {
	@EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"divisions"})
	Optional<Plant> findByName(String name);
	@EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"divisions"})
	List<Plant> findAll();
}












