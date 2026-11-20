
package com.trafficlight.service;

import com.trafficlight.model.TrafficLight;
import com.trafficlight.model.TrafficLightState;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TrafficLightService {

    private TrafficLight currentLight = TrafficLight.RED;

    private String mode = "STOPPED";

    private int remainingSeconds = 0;

    private boolean running = false;

    private boolean emergency = false;

    private String message = "Initial state";

    // Return the current traffic-light status
    public synchronized TrafficLightState getStatus() {

        return new TrafficLightState(
                currentLight.name(),
                mode,
                remainingSeconds,
                running,
                emergency,
                message
        );
    }

    // Start automatic traffic-light sequence
    public synchronized TrafficLightState start() {

        if (emergency) {
            throw new IllegalStateException(
                    "Cannot start. Reset the emergency state first."
            );
        }

        currentLight = TrafficLight.RED;
        mode = "AUTOMATIC";
        remainingSeconds = 10;
        running = true;
        message = "Normal cycle";

        return getStatus();
    }

    // Automatically update countdown every second
    @Scheduled(fixedRate = 1000)
    public synchronized void updateCountdown() {

        if (!running || !mode.equals("AUTOMATIC")) {
            return;
        }

        if (remainingSeconds > 0) {
            remainingSeconds--;
        }

        if (remainingSeconds == 0) {
            switchLight();
        }
    }

    // Switch to the next traffic light
    private void switchLight() {

        switch (currentLight) {

            case RED:
                currentLight = TrafficLight.GREEN;
                remainingSeconds = 8;
                break;

            case GREEN:
                currentLight = TrafficLight.YELLOW;
                remainingSeconds = 3;
                break;

            case YELLOW:
                currentLight = TrafficLight.RED;
                remainingSeconds = 10;
                break;
        }
    }

    // Pause the automatic sequence
    public synchronized TrafficLightState pause() {

        if (!running || !mode.equals("AUTOMATIC")) {
            throw new IllegalStateException(
                    "Cannot pause. Automatic cycle is not running."
            );
        }

        running = false;
        mode = "PAUSED";
        message = "Traffic light paused";

        return getStatus();
    }

    // Resume the paused sequence
    public synchronized TrafficLightState resume() {

        if (!mode.equals("PAUSED")) {
            throw new IllegalStateException(
                    "Cannot resume. Traffic light is not paused."
            );
        }

        if (emergency) {
            throw new IllegalStateException(
                    "Cannot resume during emergency."
            );
        }

        running = true;
        mode = "AUTOMATIC";
        message = "Normal cycle resumed";

        return getStatus();
    }

    // Stop the traffic-light sequence
    public synchronized TrafficLightState stop() {

        currentLight = TrafficLight.RED;
        remainingSeconds = 0;
        running = false;
        mode = "STOPPED";

        if (emergency) {
            message = "Emergency active. Reset required.";
        } else {
            message = "Traffic light stopped";
        }

        return getStatus();
    }

    // Reset everything to initial conditions
    public synchronized TrafficLightState reset() {

        currentLight = TrafficLight.RED;
        mode = "STOPPED";
        remainingSeconds = 0;
        running = false;
        emergency = false;
        message = "Initial state";

        return getStatus();
    }

    // Activate emergency override
    public synchronized TrafficLightState emergency() {

        currentLight = TrafficLight.RED;
        mode = "EMERGENCY";
        remainingSeconds = 0;
        running = false;
        emergency = true;
        message = "Emergency override active";

        return getStatus();
    }

    // Manually select a traffic-light colour
    public synchronized TrafficLightState manual(TrafficLight light) {

        if (emergency) {
            throw new IllegalStateException(
                    "Manual control is unavailable during emergency."
            );
        }

        if (running || mode.equals("PAUSED")) {
            throw new IllegalStateException(
                    "Stop the automatic cycle before manual control."
            );
        }

        currentLight = light;
        mode = "MANUAL";
        remainingSeconds = 0;
        running = false;
        message = "Manual control: " + light.name();

        return getStatus();
    }
}
