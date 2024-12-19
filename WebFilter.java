import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class MDCFilter implements WebFilter {

    @Override
    public Mono<Void> filter(org.springframework.web.server.ServerWebExchange exchange, WebFilterChain chain) {
        String requestId = exchange.getRequest().getId();
        return chain.filter(exchange)
                    .contextWrite(Context.of("requestId", requestId))
                    .doOnEach(signal -> {
                        if (signal.isOnNext() || signal.isOnError() || signal.isOnComplete()) {
                            MDC.put("requestId", requestId);
                        }
                        if (signal.isAfterTerminate()) {
                            MDC.clear();
                        }
                    });
    }
}
