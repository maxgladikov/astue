package astue;

import astue.model.Device;
import astue.model.Substation;
import astue.service.DeviceService;
import astue.service.SpringAux;
import astue.service.SubstationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Comparator;


@SpringBootApplication
//@EnableTransactionManagement
@EnableScheduling
public class AstueApplication implements CommandLineRunner{
	@Autowired
	private SpringAux aux;
//	@Autowired
//	private DeviceService deviceService;
	@Autowired
	private SubstationService substationService;

	public static void main(String[] args) {
		SpringApplication.run(AstueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		aux.populate();
//		ObjectMapper objectMapper=new ObjectMapper();
//		System.out.println(objectMapper.writeValueAsString(new Device()));

//		deviceService.add(Device.newBuilder().setIp("192.168.56.109")
//				.setName("TEST").setIed("F650").build());

//		substationService.getAll().stream()
//				.sorted(Comparator.comparing(Substation::getName))
//				.filter(x->x.getName().equals("SS-01"))
//				.forEach(System.out::println);
	}

}
