package astue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import astue.model.Device;
import astue.model.Division;
import astue.model.PowerRecord;
import astue.service.DivisionService;
import astue.service.PlantService;
import astue.service.RecordService;
import astue.service.implementation.report.PerformanceTestReport;
import lombok.RequiredArgsConstructor;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
public class AstueApplication implements CommandLineRunner{
	private final Environment environment;
	private final PerformanceTestReport service;
	

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AstueApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("**** START RUNNER ***");
			
		/*
		aux.createDefaultUser();
		aux.populate();
		aux.populateDevices();
		*/
		
		System.out.println("**** END RUNNER ***");
	}

}
