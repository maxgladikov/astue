package astue.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

class PowerRecordTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void test_validation_ok() {
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
		String name=" ";
		var iedType="TESYS";
		var ip="192.168.0.1";
		var power=0.1;
		Device testDevice=Device.newBuilder().setName(name).setIp(ip).setIed(iedType).setPower(power).build();
		PowerRecord underTest=new PowerRecord(testDevice,100.0,100.0);
		
		Set<ConstraintViolation<PowerRecord>> violations = validator.validate(underTest);
	    assertThat(violations.size()).isEqualTo(0);
	}
	@Test
	public void test_validation_device_notnull() {
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
		Device testDevice=null;
		PowerRecord underTest=new PowerRecord(testDevice,100.0,100.0);
		
		Set<ConstraintViolation<PowerRecord>> violations = validator.validate(underTest);
		assertThat(violations.size()).isEqualTo(1);
	}

}
