package astue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import astue.model.Substation;


public interface SubstationRepository extends JpaRepository<Substation,Long> {
	
	
	@EntityGraph(attributePaths= {"switchgears"})
//	@Query("SELECT s FROM Substation s LEFT JOIN FETCH s.switchgears WHERE  s.name=:name")
    public Optional<Substation> findByName(@Param("name") String name);
}












