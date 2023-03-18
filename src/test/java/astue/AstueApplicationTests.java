package astue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
@AutoConfigureMockMvc
@SpringBootTest//(properties = "foo=bar")
@TestPropertySource(locations = "/application-test.properties")
@ActiveProfiles("test")
class AstueApplicationTests {

	@Test
	void contextLoads() {
	}

}
