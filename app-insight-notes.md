<dependency>
    <groupId>com.microsoft.azure</groupId>
    <artifactId>applicationinsights-core</artifactId>
    <version>3.4.11</version>
</dependency>


applicationinsights.connection.string=InstrumentationKey=YOUR_INSTRUMENTATION_KEY

import com.microsoft.applicationinsights.TelemetryClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppInsightsService {
    private final TelemetryClient telemetryClient;

    public AppInsightsService(TelemetryClient telemetryClient) {
        this.telemetryClient = telemetryClient;
    }

    public void sendCustomEvent(String eventName, Map<String, String> properties) {
        telemetryClient.trackEvent(eventName, properties, null);
    }

    public void sendCustomEventWithJson(String eventName, String jsonData) {
        Map<String, String> properties = new HashMap<>();
        properties.put("jsonPayload", jsonData);
        telemetryClient.trackEvent(eventName, properties, null);
    }
}



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class AppInsightsController {
    private final AppInsightsService appInsightsService;

    public AppInsightsController(AppInsightsService appInsightsService) {
        this.appInsightsService = appInsightsService;
    }

    @GetMapping("/send-event")
    public String sendEvent(@RequestParam String eventName, @RequestParam Map<String, String> metadata) {
        appInsightsService.sendCustomEvent(eventName, metadata);
        return "Event sent to Application Insights!";
    }
}
