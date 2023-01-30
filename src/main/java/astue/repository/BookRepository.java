package astue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import astue.entity.Book;
public interface BookRepository extends JpaRepository<Book,Long>{

	Optional<Book> findBookByTitle(String title);
}
