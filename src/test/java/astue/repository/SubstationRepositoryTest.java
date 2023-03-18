package astue.repository;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import astue.model.Substation;
@DataJpaTest
@SpringBootTest
class SubstationRepositoryTest {
	
	@Autowired
	private SubstationRepository underTest;
	
	@Test
	void findByName() {
//		//given
//		String name="SS-06";
//		Substation substation=new Substation(name);
//		//when
//		underTest.save(substation);
//		Substation ssFromDB=underTest.findByName(name).orElseThrow();
//		//then
//		assertThat(ssFromDB.getName()).isEqualTo(name);
	}

}
