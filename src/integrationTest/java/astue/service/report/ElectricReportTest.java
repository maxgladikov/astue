package astue.service.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import astue.service.implementation.report.ElectricReport;
import astue.service.implementation.report.ReportProcessService;

@SpringBootTest
@ActiveProfiles(profiles = "integration")
@Transactional
class ElectricReportTest {
	
	@Autowired
	@Qualifier("reportProcessService")
	private ReportProcessService reportService;;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void createFromPlantsTest() {
		// given
		var from=LocalDateTime.of(2023, 4, 24, 10, 0);
		var to=LocalDateTime.of(2023, 4, 25, 10, 0);
		// then
		var plants=reportService.getPlants(from, to);
		var actual=ElectricReport.createFromPlants(plants);
		assertThat(actual).isNotNull();
	}

}
