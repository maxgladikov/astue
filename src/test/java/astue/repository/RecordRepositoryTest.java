package astue.repository;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import astue.model.Device;
import astue.model.Record;

@DataJpaTest
//@Disabled
class RecordRepositoryTest {
	@Autowired
	RecordRepository repository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		//given
		Device device_1=Device.newBuilder().setName("device_1").setIp("192.168.0.1").setIed("TESYS").build();
		Device device_2=Device.newBuilder().setName("device_2").setIp("192.168.0.2").setIed("F650").build();
		Record record_1=new Record(device_1,100.0,200.0);
		repository.save(record_1);
		//when
		List<Record> records=repository.findAll();
		//then
		assertThat(records.size()).isEqualTo(1);
	}

}
