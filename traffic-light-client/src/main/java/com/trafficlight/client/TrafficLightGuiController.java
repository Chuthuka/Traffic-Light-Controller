
package com.trafficlight.client;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TrafficLightGuiController {

    private final TrafficLightPanel panel;
    private final TrafficLightApiClient apiClient;

    private final AtomicBoolean requestInProgress =
            new AtomicBoolean(false);

    private final Timer refreshTimer;

    @FunctionalInterface
    private interface ApiCall {
        TrafficLightState execute() throws Exception;
    }

    public TrafficLightGuiController(
            TrafficLightPanel panel,
            TrafficLightApiClient apiClient) {

        this.panel = panel;
        this.apiClient = apiClient;

        // Connect automatic control buttons
        panel.getStartButton().addActionListener(
                e -> performRequest(
                        () -> apiClient.start()
                )
        );

        panel.getPauseButton().addActionListener(
                e -> performRequest(
                        () -> apiClient.pause()
                )
        );

        panel.getResumeButton().addActionListener(
                e -> performRequest(
                        () -> apiClient.resume()
                )
        );

        panel.getStopButton().addActionListener(
                e -> performRequest(
                        () -> apiClient.stop()
                )
        );

        // Connect reset and emergency buttons
        panel.getResetButton().addActionListener(
                e -> performRequest(
                        () -> apiClient.reset()
                )
        );

        panel.getEmergencyButton().addActionListener(
                e -> performRequest(
                        () -> apiClient.emergency()
                )
        );

        // Connect manual control buttons
        panel.getManualRedButton().addActionListener(
                e -> performRequest(
                        () -> apiClient.manual("RED")
                )
        );

        panel.getManualYellowButton().addActionListener(
                e -> performRequest(
                        () -> apiClient.manual("YELLOW")
                )
        );

        panel.getManualGreenButton().addActionListener(
                e -> performRequest(
                        () -> apiClient.manual("GREEN")
                )
        );

        // Refresh backend status every second
        refreshTimer = new Timer(1000, e -> refreshStatus());

        refreshTimer.start();

        // Get the initial backend status
        refreshStatus();
    }

    // Execute HTTP requests in the background
    private void performRequest(ApiCall apiCall) {

        if (!requestInProgress.compareAndSet(false, true)) {
            return;
        }

        panel.updateMessage("Processing request...");

        SwingWorker<TrafficLightState, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected TrafficLightState doInBackground()
                            throws Exception {

                        return apiCall.execute();
                    }

                    @Override
                    protected void done() {

                        try {

                            TrafficLightState state = get();

                            panel.updateConnectionStatus(true);
                            updateDisplay(state);

                        } catch (Exception exception) {

                            handleError(exception);

                        } finally {

                            requestInProgress.set(false);
                        }
                    }
                };

        worker.execute();
    }

    // Retrieve the latest backend status
    private void refreshStatus() {

        if (!requestInProgress.compareAndSet(false, true)) {
            return;
        }

        SwingWorker<TrafficLightState, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected TrafficLightState doInBackground()
                            throws Exception {

                        return apiClient.getStatus();
                    }

                    @Override
                    protected void done() {

                        try {

                            TrafficLightState state = get();

                            panel.updateConnectionStatus(true);
                            updateDisplay(state);

                        } catch (Exception exception) {

                            handleError(exception);

                        } finally {

                            requestInProgress.set(false);
                        }
                    }
                };

        worker.execute();
    }

    // Update GUI using backend state
    private void updateDisplay(TrafficLightState state) {

        panel.updateTrafficLights(state.getCurrentLight());

        panel.updateCountdown(state.getRemainingSeconds());

        panel.updateMode(state.getMode());

        panel.updateMessage(state.getMessage());
    }

    // Handle backend and connection errors
    private void handleError(Exception exception) {

        Throwable cause = exception;

        while (cause.getCause() != null) {
            cause = cause.getCause();
        }

        if (cause instanceof TrafficLightApiClient.ApiException) {

            TrafficLightApiClient.ApiException apiException =
                    (TrafficLightApiClient.ApiException) cause;

            panel.updateConnectionStatus(true);

            panel.updateMessage(
                    "Error " + apiException.getStatusCode()
                            + ": " + apiException.getMessage()
            );

        } else {

            panel.updateConnectionStatus(false);

            panel.updateMessage(
                    "Backend connection error. Check the server."
            );
        }
    }
}
