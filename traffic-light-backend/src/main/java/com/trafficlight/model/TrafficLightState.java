
package com.trafficlight.model;

public record TrafficLightState(
        String currentLight,
        String mode,
        int remainingSeconds,
        boolean running,
        boolean emergency,
        String message
) {
}
