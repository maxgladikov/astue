package astue.repository;

import astue.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecordRepository extends JpaRepository<Record,Long> {

}
