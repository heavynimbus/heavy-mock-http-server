package heavynimbus.server.mapper;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QueryMapper {

	public Map<String, List<String>> toMap(String query) {
		Map<String, List<String>> map = new HashMap<>();
		if (query == null) {
			return map;
		}
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			String[] keyValue = pair.split("=");
			String key = keyValue[0];
			String value = keyValue[1];
			if (map.containsKey(key)) {
				map.get(key).add(value);
			} else {
				List<String> values = new ArrayList<>();
				values.add(value);
				map.put(key, values);
			}
		}
		return map;
	}
}
