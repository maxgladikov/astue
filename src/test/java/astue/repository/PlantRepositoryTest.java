package astue.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import astue.model.Plant;
@DataJpaTest
class PlantRepositoryTest {
	@Autowired
	private PlantRepository plantRepository;
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test_commit_one() {
//		given
		String name="name";
		Plant plant=new Plant(name);
//		when
		plantRepository.save(plant);
//		then
		assertThat(plantRepository.count()).isEqualTo(1);
	}

}
