import org.slf4j.MDC;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class MDCContextUtil {

    public static <T> Mono<T> withMDC(Mono<T> mono, String key, String value) {
        return mono.contextWrite(Context.of(key, value))
                   .doOnEach(signal -> {
                       if (signal.isOnNext() || signal.isOnError() || signal.isOnComplete()) {
                           String contextValue = signal.getContextView().getOrDefault(key, null);
                           if (contextValue != null) {
                               MDC.put(key, contextValue);
                           }
                       }
                       if (signal.isAfterTerminate()) {
                           MDC.remove(key);
                       }
                   });
    }
}
