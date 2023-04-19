package astue.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@Disabled
class IndexControllerTest {
	private static final String END_POINT_PATH="/";
	
	@Autowired private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
//	@WithMockUser(username="testUser",roles= {"ADMIN"})
	void test_get() throws Exception {
		// given
		String name="someName";
		this.mockMvc
			.perform(MockMvcRequestBuilders
					.get(END_POINT_PATH)
					
//						.with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("ADMIN"))
					)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("text/html"));
		
	}

}
