package astue.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import astue.model.User;


public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByUsername(String name);
	Optional<User> findByPassword(String password);
}












