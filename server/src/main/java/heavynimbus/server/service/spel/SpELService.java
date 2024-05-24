package heavynimbus.server.service.spel;

import heavynimbus.server.mapper.SpELMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpELService {
	private final SpELMapper spELMapper;

	public Object parseExpression(HttpServletRequest request, String expression) {
		StandardEvaluationContext context = new StandardEvaluationContext(spELMapper.toContextModel(request));
		ExpressionParser parser = new SpelExpressionParser();

		return Optional.ofNullable(expression)
				.map(parser::parseExpression)
				.map(e -> e.getValue(context, Object.class))
				.orElse(expression);
	}
}
