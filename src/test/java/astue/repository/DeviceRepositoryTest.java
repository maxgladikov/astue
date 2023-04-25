package astue.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import astue.model.Device;
import astue.service.implementation.SpringAux;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
//@SpringBootTest
@DataJpaTest
//@Disabled
class DeviceRepositoryTest {
	@Autowired private  DeviceRepository deviceRepository;
	
	
@BeforeEach	
void setUp() throws Exception {
	}
@BeforeAll
public static void setupValidatorInstance() {
    
}

	

	@Test
	void test_commit_one() {
		var name="device_1";
		var iedType="TESYS";
		var ip="192.168.0.1";
		Device testDevice=Device.newBuilder().setName(name).setIp(ip).setIed(iedType).build();
		deviceRepository.save(testDevice);
		int expectedAmount=1;
		var deviceRetrived=deviceRepository.findAll().get(0);
		assertThat(deviceRepository.findAll().size()).isEqualTo(expectedAmount);
		assertThat(deviceRetrived.getName()).isEqualTo(name);
		assertThat(deviceRetrived.getHostAddress()).isEqualTo(ip);
		assertThat(deviceRetrived.getIed().name()).isEqualTo(iedType);
	}

	@Test
	void test_findByName() {
		// given
		var name="device_1";
		var iedType="TESYS";
		var ip="192.168.0.1";
		Device testDevice=Device.newBuilder().setName(name).setIp(ip).setIed(iedType).build();
		deviceRepository.save(testDevice);
		// when
		String expected=deviceRepository.findByName(name).orElseThrow().getName();
		// then
		assertThat(deviceRepository.findByName(name).orElseThrow().getName()).isEqualTo(name);
	}
	

}
