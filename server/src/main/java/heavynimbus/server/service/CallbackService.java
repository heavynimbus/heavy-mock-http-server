package heavynimbus.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CallbackService {

  //	private final RestClient restClient;
  /*
  public void handleCallbacks(List<Callback> callbacks, long start) {
  	List<CompletableFuture<?>> syncCallbacks = new ArrayList<>();
  	for (Callback callback : callbacks) {
  		if (callback.isAsync()) {
  			syncCallbacks.add(sendCallbackAsync(callback));
  			continue;
  		}
  		sendCallback(callback);
  	}
  }

  public void registerCallback(Callback callback) {
  	log.info("Registering callback: " + callback);
  	if (callback.isAsync()) {
  		sendCallbackAsync(callback);
  		return;
  	}
  	sendCallback(callback);
  }

  @Async
  protected CompletableFuture<Void> sendCallbackAsync(Callback callback) {
  	log.info("Sending callback asynchronously: " + callback);
  	sendCallback(callback);
  	return CompletableFuture.completedFuture(null);
  }
  */

  /*private void sendCallback(Callback callback) {
  	DelayUtils.delay(callback.getDelay());
  	callback.getHeaders();

  	restClient.method(HttpMethod.valueOf(callback.getMethod().name()))
  			.uri(callback.getUrl())
  			.headers(headers -> callback.getHeaders().forEach(headers::set))
  			.body(callback.getBody())
  			.retrieve()
  			.onStatus(HttpStatusCode::isError, (req, res) -> {
  				log.error("Callback {} {} failed with status {}", req.getMethod(), req.getURI(), res.getStatusCode());
  			});
  }*/

}
