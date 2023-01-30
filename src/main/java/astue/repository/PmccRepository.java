package astue.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import astue.entity.PMCC;


public interface PmccRepository extends CrudRepository<PMCC,Long>{
	List<PMCC> findByTag(String tag);
	List<PMCC> findAll();
//	void save(PMCC PMCC);
	Optional<PMCC> findById(Long id);
//	 @Query("DELETE FROM User t where t.id = :id") 
//	Long deleteById(@Param("id") Long id);
	  void delete(PMCC pmcc);
}












