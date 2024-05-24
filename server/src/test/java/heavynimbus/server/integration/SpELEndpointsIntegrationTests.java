package heavynimbus.server.integration;

import heavynimbus.server.configuration.properties.MockConfigurationProperties;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest(
		webEnvironment = RANDOM_PORT,
		properties = {
				"heavy.mock.config=classpath:configs/spel-endpoints.yml",
		})
@EnableConfigurationProperties(MockConfigurationProperties.class)
public class SpELEndpointsIntegrationTests implements IntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@TestFactory
	public List<DynamicTest> testHelloJsonFR() throws Exception {
		List<String> names = List.of("Alice", "Bob", "Charlie");
		List<String> langs = List.of("fr", "en");
		List<DynamicTest> tests = new ArrayList<>();

		for (String name : names) {
			for (String lang : langs) {
				tests.add(DynamicTest.dynamicTest("GET /hello/request?name=%s&lang=%s".formatted(name, lang), () -> {
					mockMvc.perform(
									get(getHelloUri("request"))
											.queryParam("name", name)
											.queryParam("lang", lang)
											.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
							)
							.andDo(print())
							.andExpect(status().isOk())
							.andExpect(header().string("Content-Type", "application/json"))
							.andExpect(content().json("""
									{
										"path": "/hello/request",
										"method": "GET",
										"query": {
											"name": ["%s"],
											"lang": ["%s"]
										},
										headers: {
											"Accept": ["application/json"]
										},
										"message": "%s, %s!"
									}
									""".formatted(name, lang, "fr".equals(lang) ? "Bonjour" : "Hello", name)));
				}));
			}
		}

		return tests;
	}
}
