```
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AppInsightsHttpClient {
    private static final String INSTRUMENTATION_KEY = "YOUR_INSTRUMENTATION_KEY";
    private static final String ENDPOINT = "https://dc.services.visualstudio.com/v2/track";

    public static void sendCustomEvent(String eventName, String jsonPayload) {
        try {
            URL url = new URL(ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // JSON body with iKey inside
            String payload = "{"
                    + "\"name\": \"" + eventName + "\","
                    + "\"time\": \"" + java.time.Instant.now() + "\","
                    + "\"iKey\": \"" + INSTRUMENTATION_KEY + "\","
                    + "\"data\": {"
                    + "    \"baseType\": \"EventData\","
                    + "    \"baseData\": {"
                    + "        \"name\": \"" + eventName + "\","
                    + "        \"properties\": " + jsonPayload
                    + "    }"
                    + "}"
                    + "}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```


```
curl -X POST "https://dc.services.visualstudio.com/v2/track" \
     -H "Content-Type: application/json" \
     -d '{
         "name": "TestEvent",
         "time": "2025-03-12T10:00:00Z",
         "iKey": "YOUR_INSTRUMENTATION_KEY",
         "data": {
             "baseType": "EventData",
             "baseData": {
                 "name": "MyTestEvent",
                 "properties": { "customProperty": "testValue" }
             }
         }
     }'

```



```
<dependency>
    <groupId>com.microsoft.azure</groupId>
    <artifactId>applicationinsights-core</artifactId>
    <version>3.4.11</version>
</dependency>
```
```
applicationinsights.connection.string=InstrumentationKey=YOUR_INSTRUMENTATION_KEY
```
```
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

```
