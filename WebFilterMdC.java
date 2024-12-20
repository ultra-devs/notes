


import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;

@Component
public class MDCWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Extract session ID, URI, and specific header
        String sessionId = exchange.getRequest().getCookies().getFirst("SESSIONID") != null ?
                exchange.getRequest().getCookies().getFirst("SESSIONID").getValue() : "unknown";
        String requestUri = exchange.getRequest().getURI().toString();
        String requestIdHeader = exchange.getRequest().getHeaders().getFirst("X-Request-ID");

        // Push values to MDC
        MDC.put("sessionId", sessionId);
        MDC.put("requestUri", requestUri);
        MDC.put("requestId", requestIdHeader != null ?requestIdHeader: UUID.randomUUID().toString() );

        // Continue the request processing
        return chain.filter(exchange)
                .doFinally(signalType -> MDC.clear()); // Clear MDC after the request is processed
    }
}
