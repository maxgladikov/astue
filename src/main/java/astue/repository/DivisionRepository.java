package astue.repository;

import astue.model.Division;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DivisionRepository extends JpaRepository<Division,Long> {
	@EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"plant","devices"})
	Optional<Division> findByName(String name);
}












