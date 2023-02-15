package astue.repository;

import java.util.Optional;

import astue.model.Substation;
import org.springframework.data.jpa.repository.JpaRepository;
import astue.model.Switchgear;
import org.springframework.data.jpa.repository.Query;


public interface SwitchgearRepository extends JpaRepository<Switchgear,Long> {
	Optional<Switchgear> findByName(String name);
	//@Query(value = "SELECT s FROM Switchgear s WHERE s.id=?1  join Subs")
//	Optional<Switchgear> findById(Long id);
//	Optional<Switchgear> findBySubstation(Substation substation);
}












