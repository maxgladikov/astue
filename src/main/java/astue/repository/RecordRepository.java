package astue.repository;

import astue.model.Record;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecordRepository extends JpaRepository<Record,Long> {
	 @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"device"})
		Optional<List<Record>> findAllByCreatedBetween(LocalDateTime from,LocalDateTime to);
}
