package astue.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

class UserTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void test_no_validation() {
		String userName="name";
		String password="password";
		User underTest=new User();
		underTest.setUsername(userName);
		underTest.setPassword(password);
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
	    Set<ConstraintViolation<User>> violations = validator.validate(underTest);
	    
	    assertThat(violations.size()).isEqualTo(0);
	}
	@Test
	public void test_validation_for_blank_name() {
		String userName=" ";
		String password="password";
		User underTest=new User();
		underTest.setUsername(userName);
		underTest.setPassword(password);
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(underTest);
		
		assertThat(violations.size()).isEqualTo(1);
	}
	@Test
	public void test_validation_for_blank_password() {
		String userName="name";
		String password=" ";
		User underTest=new User();
		underTest.setUsername(userName);
		underTest.setPassword(password);
		var validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(underTest);
		
		assertThat(violations.size()).isEqualTo(1);
	}

}
