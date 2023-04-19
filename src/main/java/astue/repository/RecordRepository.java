package astue.repository;

import astue.model.PowerRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecordRepository extends JpaRepository<PowerRecord,Long> {
	 @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"device"})
		Optional<List<PowerRecord>> findAllByCreatedBetween(LocalDateTime from,LocalDateTime to);
}
