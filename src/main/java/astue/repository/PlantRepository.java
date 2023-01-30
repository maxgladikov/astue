package astue.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import astue.entity.Plant;


public interface PlantRepository extends CrudRepository<Plant,Long>{
	List<Plant> findByNumber(Integer number);
	List<Plant> findAll();
//	void save(Plant plant);
	Optional<Plant> findById(Long id);
//	 @Query("DELETE FROM User t where t.id = :id") 
//	Long deleteById(@Param("id") Long id);
	  void delete(Plant plant);
}












