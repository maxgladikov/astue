package astue.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import astue.model.Device;
import astue.service.SpringAux;
//@SpringBootTest
@DataJpaTest
@Disabled
class DeviceRepositoryTest {
	@Autowired
private  DeviceRepository deviceRepository;
	
	@MockBean
	private SpringAux aux;
	
@BeforeEach	
void setUp() throws Exception {
	
	}

	@Test
	void findByName() {
		// given
		String name="TestingDevice";
		Device device=Device.newBuilder().setConsumer(true).setName(name).setIed("TESYS").setDescription("UnderTest").setIp("192.168.0.1")
															.build();
		deviceRepository.save(device);
		// when
		String expected=deviceRepository.findByName(name).orElseThrow().getName();
		// then
		assertThat(expected).isEqualTo(name);
	}

}
