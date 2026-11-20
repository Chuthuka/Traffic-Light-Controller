
package com.trafficlight.client;

import javax.swing.SwingUtilities;

public class TrafficLightClientApplication {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TrafficLightPanel panel = new TrafficLightPanel();

            TrafficLightApiClient apiClient =
                    new TrafficLightApiClient();

            new TrafficLightGuiController(
                    panel,
                    apiClient
            );

            panel.setVisible(true);

        });
    }
}
