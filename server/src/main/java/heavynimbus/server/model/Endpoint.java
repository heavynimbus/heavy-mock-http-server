package heavynimbus.server.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endpoint {

  private String name;

  @Valid private Request request;

  @Valid private Response response;

  @NotNull private List<@Valid @NotNull Callback> callbacks = List.of();

  public boolean supports(HttpServletRequest httpServletRequest) {
    if (request == null) return true;
    return request.supports(httpServletRequest);
  }
}
