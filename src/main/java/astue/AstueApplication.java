package astue;

import astue.model.Device;
import astue.model.Role;
import astue.model.Substation;
import astue.model.User;
import astue.service.DeviceService;
import astue.service.SpringAux;
import astue.service.SubstationService;
import astue.service.UserService;
import astue.util.Ied;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.Comparator;


@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
//@EnableTransactionManagement
@EnableScheduling
@RequiredArgsConstructor
public class AstueApplication implements CommandLineRunner{
	private final SpringAux aux;
	private final UserService userService;
	private final PasswordEncoder encoder;
//	private final DeviceService deviceService;
//	private final SubstationService substationService;

	public static void main(String[] args) {
		SpringApplication.run(AstueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("**** START RUNNER ***");
		User user=User.builder().active(true).username("max").password(encoder.encode("pass")).role(Role.ADMIN).build();
//		userService.createUser(user);
//		aux.populate();
//		ObjectMapper objectMapper=new ObjectMapper();
//		System.out.println(objectMapper.writeValueAsString(new Device()));
		System.out.println("**** END RUNNER ***");
	}

}
