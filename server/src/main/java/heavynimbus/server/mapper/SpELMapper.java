package heavynimbus.server.mapper;

import heavynimbus.server.service.spel.SpELContextModel;
import heavynimbus.server.service.spel.SpELContextRequestModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpELMapper {
	private final HeadersMapper headersMapper;
	private final QueryMapper queryMapper;

	public SpELContextModel toContextModel(HttpServletRequest request) {
		return SpELContextModel.builder()
				.request(SpELContextRequestModel.builder()
						.path(request.getRequestURI())
						.method(request.getMethod())
						.query(queryMapper.toMap(request.getQueryString()))
						.headers(headersMapper.toMap(request))
						.build())
				.build();
	}

}
