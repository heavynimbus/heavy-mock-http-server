package heavynimbus.server.configuration.filter;

import heavynimbus.server.util.TrackIdHighlighting;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AnalysisFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
	                                @NonNull HttpServletResponse response,
	                                @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		long start = System.currentTimeMillis();
		TrackIdHighlighting.generateTrackId();
		String query = request.getQueryString() == null ? "" : "?" + request.getQueryString();
		log.info("Received request: {} {}{}", request.getMethod(), request.getRequestURI(), query);
		request.setAttribute("start", start);

		filterChain.doFilter(request, response);

		long end = System.currentTimeMillis();
		log.info("Request: {} {}{} took {}ms", request.getMethod(), request.getRequestURI(), query, end - start);;
		TrackIdHighlighting.clearTrackId();
	}
}
