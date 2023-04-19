package astue.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

class DeviceTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void test_validation_for_blank_name() {
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
		String name=" ";
		var iedType="TESYS";
		var ip="192.168.0.1";
		var power=0.1;
		Device testDevice=Device.newBuilder().setName(name).setIp(ip).setIed(iedType).setPower(power).build();
		Set<ConstraintViolation<Device>> violations = validator.validate(testDevice);
	    assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void test_validation_for_incorrect_ip() {
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
		String name="name";
		var iedType="TESYS";
		var ip="1925.168.0.1";
		var power=0.1;
		Device testDevice=Device.newBuilder().setName(name).setIp(ip).setIed(iedType).setPower(power).build();
		Set<ConstraintViolation<Device>> violations = validator.validate(testDevice);
		
		assertThat(violations.size()).isEqualTo(1);
	}
	@Test
	public void test_validation_for_negative_power() {
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
		String name="name";
		var iedType="TESYS";
		var ip="192.168.0.1";
		var power=-0.1;
		Device testDevice=Device.newBuilder().setName(name).setIp(ip).setIed(iedType).setPower(power).build();
		Set<ConstraintViolation<Device>> violations = validator.validate(testDevice);
		
		assertThat(violations.size()).isEqualTo(1);
	}

}
