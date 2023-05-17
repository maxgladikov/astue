package astue;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = {BeanConfiguration.class,DataSourceConfig.class,WebConfig.class}) 
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//		(
//		  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
//		  classes = {AstueApplication.class}
//		  )
@ActiveProfiles(profiles = "integration")
//@AutoConfigureMockMvc
class AstueApplicationTests {
	
	 @LocalServerPort
	  private Integer port;
	 @Autowired
	  private  TestRestTemplate testRestTemplate;
	
	  @Test
	  void contextLoads(ApplicationContext context) {
	    assertThat(context).isNotNull();
	  }
	  
	  @Test
	  void hasIndieAuthControllerConfigured(ApplicationContext context) {
	    assertThat(context.getBean(PasswordEncoder.class)).isNotNull();
	  }
	  
//	  @Test
//	  public void accessApplication() {
//		  assertThat(port).isEqualTo(8080);
//	  }

	

	
	
//	assertThatThrownBy(() -> {
//	    List<String> list = Arrays.asList("String one", "String two");
//	    list.get(2);
//	}).isInstanceOf(IndexOutOfBoundsException.class)
//	  .hasMessageContaining("Index: 2, Size: 2");
//	.hasMessage("Index: %s, Size: %s", 2, 2)
//	.hasMessageStartingWith("Index: 2")
//	.hasMessageContaining("2")
//	.hasMessageEndingWith("Size: 2")
//	.hasMessageMatching("Index: \\d+, Size: \\d+")
//	.hasCauseInstanceOf(IOException.class)
//	.hasStackTraceContaining("java.io.IOException");

}
