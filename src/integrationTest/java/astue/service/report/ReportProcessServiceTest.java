package astue.service.report;


import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import astue.model.Plant;
import astue.service.implementation.report.ReportProcessService;

@SpringBootTest
@ActiveProfiles(profiles = "integration")
@Transactional
class ReportProcessServiceTest {
	
	@Autowired
	@Qualifier("reportProcessService")
	private ReportProcessService underTest;
	
	@Test
	void getPlantsTest() {
		// given
		var from=LocalDateTime.of(2023, 4, 24, 10, 0);
		var to=LocalDateTime.of(2023, 4, 26, 10, 0);
		// then
		var plants=underTest.getPlants(from, to);
		// when
		
		assertThat(plants).isNotNull();
		plants.stream().flatMap(p -> p.getDivisions().stream()).peek(div -> assertThat(div).isNotNull())
		.flatMap(div -> div.getDevices().stream()).peek(dev-> assertThat(dev).isNotNull()).filter(dev -> dev.isConsumer())
		.flatMap(dev -> dev.getRecords().stream()).forEach(rec -> assertThat(rec).isNotNull());
		assertThat(plants.contains(new Plant("A-20"))).isTrue();
	}
	
	

}
