package astue.repository;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
@DataJpaTest

@Disabled
class SubstationRepositoryTest {
	
	@Autowired
	private SubstationRepository underTest;
	@Disabled
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
