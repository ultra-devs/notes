

import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MDCWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Extract session ID, URI, and specific headers
        String sessionId = exchange.getRequest().getCookies().getFirst("JSESSIONID") != null ?
                exchange.getRequest().getCookies().getFirst("JSESSIONID").getValue() : "unknown";
        String requestUri = exchange.getRequest().getURI().toString();

        // Extract headers in order of priority
        String requestId = getRequestIdFromHeaders(exchange);

        long startTime = System.currentTimeMillis();

        // Push values to MDC
        MDC.put("sessionId", sessionId);
        MDC.put("requestUri", requestUri);
        MDC.put("requestId", requestId);

        // Continue the request processing
        return chain.filter(exchange)
                .doFinally(signalType -> {
                    // Calculate the response time
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;

                    // Log the message
                    log.info("Transaction completed. sessionId={}, requestUri={}, requestId={}, responseTime={}ms",
                            sessionId, requestUri, requestId, duration);

                    // Clear MDC after the request is processed
                    MDC.clear();
                });
    }

    private String getRequestIdFromHeaders(ServerWebExchange exchange) {
        // Check for each header in the order of priority and return the first non-null value
        String[] headersToCheck = {"transactionId", "X-Correlation-ID", "traceId", "X-Request-ID"};
        
        for (String header : headersToCheck) {
            String headerValue = exchange.getRequest().getHeaders().getFirst(header);
            if (headerValue != null && !headerValue.isEmpty()) {
                return headerValue;
            }
        }

        // If none of the headers are present, generate a new UUID
        return UUID.randomUUID().toString();
    }
}
