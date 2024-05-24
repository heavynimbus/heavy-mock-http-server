package heavynimbus.server.service.spel;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class SpELContextRequestModel {
	public String path;
	public String method;
	public Map<String, List<String>> query;
	public Map<String, List<String>> headers;
}
