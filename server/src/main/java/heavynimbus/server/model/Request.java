package heavynimbus.server.model;

import heavynimbus.server.util.QueryParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {
	@NotNull
	private List<@NotNull HttpMethod> methods;

	@NotNull
	private List<String> paths;

	private Map<@NotNull String, @NotNull String> headers;

	private Map<@NotNull String, String> query;

	public boolean supports(HttpServletRequest request) {
		boolean anyMethodMatches = methods.stream()
				.map(HttpMethod::name)
				.anyMatch(method -> method.equals(request.getMethod()));
		if (!anyMethodMatches) return false;

		boolean anyPathMatches = paths.stream()
				.anyMatch(path -> request.getRequestURI().matches(path));
		if (!anyPathMatches) return false;

		if (headers != null) {
			for (String key : headers.keySet()) {
				String expectedHeaderValue = headers.get(key);
				String requestHeaderValue = request.getHeader(key);
				if (!Objects.equals(expectedHeaderValue, requestHeaderValue)) return false;
			}
		}

		if (query == null) {
			return true;
		}

		String requestQueryString = request.getQueryString();
		MultiValueMap<String, String> requestQueryMap = QueryParser.parse(requestQueryString);

		Set<String> requestQueryKeys = requestQueryMap.keySet();
		if (!requestQueryKeys.containsAll(query.keySet())) return false;

		for (String key : query.keySet()) {
			String expectedValue = query.get(key);
			List<String> requestValues = requestQueryMap.get(key);
			if (requestValues == null) return false;
			if (!requestValues.contains(expectedValue)) return false;
		}

		return true;
	}
}
