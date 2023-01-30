package astue.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import astue.entity.Substation;


public interface SubstationRepository extends CrudRepository<Substation,Long>{
	List<Substation> findByNumber(Integer number);
	List<Substation> findAll();
//	void save(Substation Substation);
	Optional<Substation> findById(Long id);
//	 @Query("DELETE FROM User t where t.id = :id") 
//	Long deleteById(@Param("id") Long id);
	  void delete(Substation Substation);
}












