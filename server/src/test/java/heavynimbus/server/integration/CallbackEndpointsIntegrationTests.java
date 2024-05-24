package heavynimbus.server.integration;


import heavynimbus.server.configuration.properties.MockConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureMockMvc
@SpringBootTest(
		webEnvironment = RANDOM_PORT,
		properties = {
				"heavy.mock.config=classpath:configs/delayed-endpoints.yml",
		})
@EnableConfigurationProperties(MockConfigurationProperties.class)
public class CallbackEndpointsIntegrationTests {
}
