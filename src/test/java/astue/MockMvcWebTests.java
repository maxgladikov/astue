//package astue;
//
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import lombok.RequiredArgsConstructor;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(
//classes = ReadingListApplication.class)
//@WebAppConfiguration
//@RequiredArgsConstructor
//public class MockMvcWebTests {
//		private final WebApplicationContext webContext;
//		private MockMvc mockMvc;
//		@Before
//		public void setupMockMvc() {
//		mockMvc = MockMvcBuilders
//		.webAppContextSetup(webContext)
//		.build();
//	}
//	@Test
//	public void homePage() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/readingList"))
//		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andExpect(MockMvcResultMatchers.view().name("readingList"))
//		.andExpect(MockMvcResultMatchers.model().attributeExists("books"))
//		.andExpect(MockMvcResultMatchers.model().attribute("books",
//		Matchers.is(Matchers.empty())));
//	}
//}
