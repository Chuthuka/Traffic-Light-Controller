
package com.trafficlight.controller;

import com.trafficlight.model.TrafficLight;
import com.trafficlight.model.TrafficLightState;
import com.trafficlight.service.TrafficLightService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/traffic")
public class TrafficLightController {

    private final TrafficLightService trafficLightService;

    public TrafficLightController(
            TrafficLightService trafficLightService) {
        this.trafficLightService = trafficLightService;
    }

    // 1. Get current traffic-light status
    @GetMapping("/status")
    public ResponseEntity<TrafficLightState> getStatus() {

        return ResponseEntity.ok(
                trafficLightService.getStatus()
        );
    }

    // 2. Start automatic sequence
    @PostMapping("/start")
    public ResponseEntity<TrafficLightState> start() {

        return ResponseEntity.ok(
                trafficLightService.start()
        );
    }

    // 3. Pause automatic sequence
    @PostMapping("/pause")
    public ResponseEntity<TrafficLightState> pause() {

        return ResponseEntity.ok(
                trafficLightService.pause()
        );
    }

    // 4. Resume automatic sequence
    @PostMapping("/resume")
    public ResponseEntity<TrafficLightState> resume() {

        return ResponseEntity.ok(
                trafficLightService.resume()
        );
    }

    // 5. Stop traffic-light sequence
    @PostMapping("/stop")
    public ResponseEntity<TrafficLightState> stop() {

        return ResponseEntity.ok(
                trafficLightService.stop()
        );
    }

    // 6. Reset traffic-light controller
    @PostMapping("/reset")
    public ResponseEntity<TrafficLightState> reset() {

        return ResponseEntity.ok(
                trafficLightService.reset()
        );
    }

    // 7. Activate emergency override
    @PostMapping("/emergency")
    public ResponseEntity<TrafficLightState> emergency() {

        return ResponseEntity.ok(
                trafficLightService.emergency()
        );
    }

    // 8. Manually select traffic-light colour
    @PostMapping("/manual/{light}")
    public ResponseEntity<TrafficLightState> manual(
            @PathVariable String light) {

        TrafficLight selectedLight;

        try {
            selectedLight = TrafficLight.valueOf(
                    light.toUpperCase()
            );
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Invalid light. Choose RED, GREEN or YELLOW."
            );
        }

        return ResponseEntity.ok(
                trafficLightService.manual(selectedLight)
        );
    }
}
