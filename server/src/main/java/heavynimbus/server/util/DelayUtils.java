package heavynimbus.server.util;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DelayUtils {

	public static void delayExactly(long start, long delay) {
		long now = System.currentTimeMillis();
		long elapsed = now - start;
		if (elapsed < delay) {
			delay(delay - elapsed);
		}
	}

	public static void delay(long delay) {
		if (delay <= 0) {
			return;
		}
		try {
			log.debug("Delaying for {} ms", delay);
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
}
