package astue.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import astue.model.Plant;
import astue.security.JwtService;
import astue.service.interfaces.PlantService;

@WebMvcTest(controllers=PlantController.class)
class PlantControllerTest {
	private static final String END_POINT_PATH="/api/v1/plants";
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@MockBean private PlantService plantService;  
	@MockBean private JwtService jwtService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
//	@WithMockUser(username="testUser",roles= {"ADMIN"})
	void test_get_plants() throws Exception {
		// given
		String name="someName";
		Plant plant=new Plant("testPlant");
		String requestBody=objectMapper.writeValueAsString(plant);
		this.mockMvc
			.perform(MockMvcRequestBuilders
					.get(END_POINT_PATH)
						.with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("ADMIN"))
					)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
	}

}
