package astue;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties.Application;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = {BeanConfiguration.class,DataSourceConfig.class,WebConfig.class}) 
@SpringBootTest
		(
		  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		  classes = {AstueApplication.class}
		  )
//@ActiveProfiles(profiles = "integration")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
//@Disabled
class AstueApplicationTests {
	
	  @Test
	  void contextLoads(ApplicationContext context) {
	    assertThat(context).isNotNull();
	  }
	  
//	  @Test
//	  void hasIndieAuthControllerConfigured(ApplicationContext context) {
//	    assertThat(context.getBean(PasswordEncoder.class)).isNotNull();
//	  }

	
//	@Test
//	public void whenDependentClassIsPresent_thenBeanCreated() {
//	    this.contextRunner.withUserConfiguration(ConditionalOnClassConfiguration.class)
//	        .run(context -> {
//	            assertThat(context).hasBean("created");
//	            assertThat(context.getBean("created"))
//	              .isEqualTo("This is created when ConditionalOnClassIntegrationTest "
//	                         + "is present on the classpath");
//	        });
//	}

	
	
//	@Disabled
//	 @Test
//	    public void shouldPassIfStringMatches() throws Exception {
//	        assertThat(restTemplate.getForObject("http://localhost:" + port + "/hello",
//	                String.class)).contains("Hello!");
//	    }
//	
//	
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
