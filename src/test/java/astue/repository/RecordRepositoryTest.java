package astue.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;

import astue.model.Device;
import astue.model.PowerRecord;

@DataJpaTest
class RecordRepositoryTest {
	@Autowired
	RecordRepository recordRepository;
	@Autowired
	DeviceRepository deviceRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void test_commit_one() {

		//given
		String name="name";
		var iedType="TESYS";
		var ip="192.168.0.1";
		var power=0.1;
		Device testDevice=Device.newBuilder().setName(name).setIp(ip).setIed(iedType).setPower(power).build();
		PowerRecord record_1=new PowerRecord(testDevice,100.0,200.0);
		deviceRepository.save(testDevice);
		recordRepository.save(record_1);
		//when
		List<PowerRecord> records=recordRepository.findAll();
		
		//then
		assertThat(records.size()).isEqualTo(1);
//		 assertThatThrownBy(() -> repository.save(record_1))
//	      .isInstanceOf(PropertyValueException.class)
//	      .hasMessageContaining("not-null property references a null or transient value");
	}


}
