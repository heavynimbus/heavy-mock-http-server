package heavynimbus.server.mapper;

import heavynimbus.server.model.Body;
import heavynimbus.server.model.Response;
import heavynimbus.server.service.spel.SpELService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResponseMapper {

	private final SpELService spELService;

	public ResponseEntity<Object> toEntity(HttpServletRequest request, Response response) {
		var builder = ResponseEntity.status(response.getStatus());
		Optional.ofNullable(response.getHeaders())
				.orElse(Map.of())
				.entrySet()
				.stream()
				.map(entry -> Map.entry(entry.getKey(), entry.getValue().toArray(new String[0])))
				.forEach(entry -> builder.header(entry.getKey(), entry.getValue()));
		if (response.getBody() == null) return builder.build();
		Body body = response.getBody();
		if (body.getContent() != null) {
			Object content = spELService.parseExpression(request, body.getContent());
			return builder.body(content);
		} else if (body.getFile() != null) return builder.body(new FileSystemResource(new File(body.getFile())));
		return builder.build();
	}
}
