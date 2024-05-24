package heavynimbus.server.integration;

import heavynimbus.server.configuration.properties.MockConfigurationProperties;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(
		webEnvironment = RANDOM_PORT,
		properties = {
				"heavy.mock.config=classpath:configs/file-endpoints.yml",
		})
@EnableConfigurationProperties(MockConfigurationProperties.class)
public class FileEndpointsIntegrationTests implements IntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET /hello/json returns 200 with json file")
	public void testHelloJson() throws Exception {
		var url = getClass().getResource("/files/hello.json");
		assert url != null;
		byte[] expected = Files.readAllBytes(Path.of(url.toURI()));

		mockMvc.perform(
						get(getHelloUri())
								.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(result -> {
					byte[] actual = result.getResponse().getContentAsByteArray();
					Assertions.assertThat(actual.length).isEqualTo(expected.length);
					for (int i = 0; i < actual.length; i++) {
						Assertions.assertThat(actual[i]).isEqualTo(expected[i]);
					}
				});
	}

	@Test
	@DisplayName("GET /hello/html returns 200 with html file")
	public void testHelloHtml() throws Exception {
		var url = getClass().getResource("/files/hello.html");
		assert url != null;
		byte[] expected = Files.readAllBytes(Path.of(url.toURI()));

		mockMvc.perform(
						get(getHelloUri())
								.header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE)
				).andExpect(status().isOk())
				.andExpect(content().contentType("text/html"))
				.andExpect(result -> {
					byte[] actual = result.getResponse().getContentAsByteArray();
					Assertions.assertThat(actual.length).isEqualTo(expected.length);
					for (int i = 0; i < actual.length; i++) {
						Assertions.assertThat(actual[i]).isEqualTo(expected[i]);
					}
				});
	}

}
