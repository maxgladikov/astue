package astue.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import astue.model.Device;
import astue.model.Division;
import astue.model.Switchgear;
import astue.security.JwtService;
import astue.service.DeviceService;
import astue.service.DivisionService;
import astue.service.SwitchgearService;
@WebMvcTest(controllers=DeviceController.class)
class DeviceControllerTest {
	
	private static final String END_POINT_PATH="/api/v1/devices";
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@MockBean private DeviceService service;
	@MockBean private SwitchgearService switchgearService;
	@MockBean private DivisionService divisionService;
	@MockBean private JwtService jwtService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test_confirm_unauthorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get(END_POINT_PATH)
				.contentType("application/json")
				).andExpect(status().isUnauthorized());
	}
	@Test
	void test_confirm_authorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get(END_POINT_PATH)
				.with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("ADMIN"))
				.contentType("application/json")
				).andExpect(status().isOk());
	}
	
	@Test
	@Disabled
	void test_add() throws Exception {
		// given
		Switchgear swgr=new Switchgear("swgr");
		Division div=new Division("div");
		Device device=Device.newBuilder().setName("test").setIp("192.168.0.1").setIed("TESYS").setConsumer(false).setDescription("sadsa").setDrawerColumn(1).setDrawerRow('A')
				.setPower(10.0).setVoltage('L').setIncomer(false).setLine("A").setSwitchgear(swgr).setDivision(div)
				.build();
		device.setId(1l);
//		SimpleModule module = new SimpleModule();
//		module.addDeserializer(Wrapper.class, new DeviceSerializer());
//		mapper.registerModule(module);
		
		String requestBody=objectMapper.writeValueAsString(device);
		// when
		mockMvc.perform(MockMvcRequestBuilders
				.post(END_POINT_PATH)
				.with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("ADMIN"))
				.contentType("application/json")
				.content(requestBody)
				).andExpect(status().isOk());
	}
	
	

}
