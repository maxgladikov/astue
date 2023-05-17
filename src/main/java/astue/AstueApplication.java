package astue;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import lombok.RequiredArgsConstructor;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
public class AstueApplication implements CommandLineRunner{
	private final Environment environment;
	

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
