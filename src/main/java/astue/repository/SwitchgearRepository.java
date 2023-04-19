package astue.repository;

import java.util.List;
import java.util.Optional;

import astue.model.Substation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import astue.model.Switchgear;
import org.springframework.data.jpa.repository.Query;


public interface SwitchgearRepository extends JpaRepository<Switchgear,Long> {
	 @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"substation","devices"})
	Optional<Switchgear> findByName(String name);
	 
	 @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"substation","devices"})
	 List<Switchgear> findAll();
	 
	 
	 
	 
	 
	 
	//@Query(value = "SELECT s FROM Switchgear s WHERE s.id=?1  join Subs")
//	Optional<Switchgear> findById(Long id);
//	Optional<Switchgear> findBySubstation(Substation substation);
}












