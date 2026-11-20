
package com.trafficlight.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class TrafficLightApiClient {

    private static final String BASE_URL =
            "http://localhost:8080/api/traffic";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public TrafficLightApiClient() {

        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();

        objectMapper = new ObjectMapper();
    }

    // Get the current traffic-light status
    public TrafficLightState getStatus()
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/status"))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        return sendRequest(request);
    }

    // Start automatic traffic-light cycle
    public TrafficLightState start()
            throws IOException, InterruptedException {

        return postRequest("/start");
    }

    // Pause automatic traffic-light cycle
    public TrafficLightState pause()
            throws IOException, InterruptedException {

        return postRequest("/pause");
    }

    // Resume automatic traffic-light cycle
    public TrafficLightState resume()
            throws IOException, InterruptedException {

        return postRequest("/resume");
    }

    // Stop traffic-light cycle
    public TrafficLightState stop()
            throws IOException, InterruptedException {

        return postRequest("/stop");
    }

    // Reset traffic-light system
    public TrafficLightState reset()
            throws IOException, InterruptedException {

        return postRequest("/reset");
    }

    // Activate emergency mode
    public TrafficLightState emergency()
            throws IOException, InterruptedException {

        return postRequest("/emergency");
    }

    // Select a light manually
    public TrafficLightState manual(String light)
            throws IOException, InterruptedException {

        return postRequest("/manual/" + light);
    }

    // Send a POST request
    private TrafficLightState postRequest(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .timeout(Duration.ofSeconds(5))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return sendRequest(request);
    }

    // Send HTTP request and process the response
    private TrafficLightState sendRequest(HttpRequest request)
            throws IOException, InterruptedException {

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        int statusCode = response.statusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return objectMapper.readValue(
                    response.body(),
                    TrafficLightState.class
            );
        }

        String errorMessage;

        try {
            errorMessage = objectMapper
                    .readTree(response.body())
                    .path("error")
                    .asText("Request failed.");
        } catch (Exception exception) {
            errorMessage = "Request failed with HTTP status "
                    + statusCode;
        }

        throw new ApiException(statusCode, errorMessage);
    }

    // Custom exception for HTTP errors
    public static class ApiException extends IOException {

        private final int statusCode;

        public ApiException(int statusCode, String message) {
            super(message);
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}
