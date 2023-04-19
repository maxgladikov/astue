package astue.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

class BaseEntityTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	 void test_validation_for_blank_name() {
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
		String name=" ";
		BaseEntity underTest=new BaseEntity();
		underTest.setName(name);
	    Set<ConstraintViolation<BaseEntity>> violations = validator.validate(underTest);
	 
	    assertThat(violations.size()).isEqualTo(1);
	}
	@Test
	 void test_validation_for_null_name() {
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
		String name=null;
		BaseEntity underTest=new BaseEntity();
		underTest.setName(name);
		Set<ConstraintViolation<BaseEntity>> violations = validator.validate(underTest);
		
		assertThat(violations.size()).isEqualTo(1);
	}

}
