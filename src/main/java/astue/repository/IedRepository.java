package astue.repository;

import astue.model.Ied;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface IedRepository extends JpaRepository<Ied,Long> {
	Optional<Ied> findByName(String name);
}












