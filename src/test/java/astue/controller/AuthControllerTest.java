package astue.controller;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import astue.service.implementation.AuthenticationService;
import lombok.RequiredArgsConstructor;

@WebMvcTest(controllers=AuthController.class)
@AutoConfigureMockMvc(addFilters=false)
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class AuthControllerTest {

	private MockMvc mockMvc;
	@MockBean
	private AuthenticationService service;
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
//		given(service.authenticate(null))
	}

}
