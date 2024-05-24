package heavynimbus.server.mapper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HeadersMapper {

	public Map<String, List<String>> toMap(HttpServletRequest request) {
		Map<String, List<String>> map = new HashMap<>();
		Iterator<String> headerNames = request.getHeaderNames().asIterator();
		while (headerNames.hasNext()) {
			String headerName = headerNames.next();
			List<String> headerValues = new ArrayList<>();
			Enumeration<String> headers = request.getHeaders(headerName);
			while (headers.hasMoreElements()) {
				headerValues.add(headers.nextElement());
			}
			map.put(headerName, headerValues);
		}
		return map;
	}
}
