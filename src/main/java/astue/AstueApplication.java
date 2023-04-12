package astue;

import java.util.function.Function;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import astue.model.Device;
import astue.service.FieldDataService;
import lombok.RequiredArgsConstructor;


@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
@EnableScheduling
@RequiredArgsConstructor
public class AstueApplication implements CommandLineRunner{
	private final Environment environment;
//	private final SpringAux aux;
//	@Value("#{new Boolean('${custom.populate}')}")
//	private boolean populate;
//	@Value("${custom.populate}")
//	@Value("${server.port}")
	
	private final Function<Device,FieldDataService> fieldDataServiceFactory;
	
//	private final DeviceService deviceService;
//	private final SubstationService substationService;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AstueApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
//		SpringApplication.run(AstueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("**** START RUNNER ***");
//		aux.createDefaultUser();
//		System.out.println("Active profile: "+this.environment.getActiveProfiles()[0]);
//					aux.populate();
//					aux.populateDevices();
//		ObjectMapper objectMapper=new ObjectMapper();
//		System.out.println(objectMapper.writeValueAsString(new Device()));
		
		System.out.println("**** END RUNNER ***");
	}

}
