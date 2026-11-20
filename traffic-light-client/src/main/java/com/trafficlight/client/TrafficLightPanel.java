
package com.trafficlight.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TrafficLightPanel extends JFrame {

    private JLabel redLight;
    private JLabel yellowLight;
    private JLabel greenLight;

    private JLabel countdownLabel;
    private JLabel modeLabel;
    private JLabel messageLabel;
    private JLabel connectionLabel;

    private JButton startButton;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton stopButton;
    private JButton resetButton;
    private JButton emergencyButton;

    private JButton manualRedButton;
    private JButton manualYellowButton;
    private JButton manualGreenButton;

    public TrafficLightPanel() {

        setTitle("Traffic Light Controller");
        setSize(650, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 243, 247));
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Application title
        JLabel titleLabel = new JLabel("TRAFFIC LIGHT CONTROLLER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Traffic light display
        JPanel trafficLightPanel = new JPanel();
        trafficLightPanel.setLayout(new BoxLayout(
                trafficLightPanel, BoxLayout.Y_AXIS));

        trafficLightPanel.setBackground(Color.DARK_GRAY);
        trafficLightPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK, 3),
                        new EmptyBorder(15, 30, 15, 30)
                )
        );

        redLight = createLightLabel(Color.RED);
        yellowLight = createLightLabel(Color.YELLOW);
        greenLight = createLightLabel(Color.GREEN);

        trafficLightPanel.add(redLight);
        trafficLightPanel.add(Box.createVerticalStrut(12));
        trafficLightPanel.add(yellowLight);
        trafficLightPanel.add(Box.createVerticalStrut(12));
        trafficLightPanel.add(greenLight);

        trafficLightPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(trafficLightPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Countdown display
        countdownLabel = new JLabel("Remaining Time: 0 seconds");
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 22));
        countdownLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(countdownLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Mode display
        modeLabel = new JLabel("Mode: STOPPED");
        modeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(modeLabel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Automatic control buttons
        JLabel controlTitle = new JLabel("Automatic Controls");
        controlTitle.setFont(new Font("Arial", Font.BOLD, 16));
        controlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(controlTitle);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        controlPanel.setBackground(new Color(240, 243, 247));

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resumeButton = new JButton("Resume");
        stopButton = new JButton("Stop");

        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        controlPanel.add(stopButton);

        mainPanel.add(controlPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Manual control buttons
        JLabel manualTitle = new JLabel("Manual Controls");
        manualTitle.setFont(new Font("Arial", Font.BOLD, 16));
        manualTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(manualTitle);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel manualPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        manualPanel.setBackground(new Color(240, 243, 247));

        manualRedButton = new JButton("Manual Red");
        manualYellowButton = new JButton("Manual Yellow");
        manualGreenButton = new JButton("Manual Green");

        manualPanel.add(manualRedButton);
        manualPanel.add(manualYellowButton);
        manualPanel.add(manualGreenButton);

        mainPanel.add(manualPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Emergency and reset buttons
        JPanel safetyPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        safetyPanel.setBackground(new Color(240, 243, 247));

        resetButton = new JButton("Reset");
        emergencyButton = new JButton("EMERGENCY");

        emergencyButton.setBackground(Color.RED);
        emergencyButton.setForeground(Color.WHITE);
        emergencyButton.setOpaque(true);

        safetyPanel.add(resetButton);
        safetyPanel.add(emergencyButton);

        mainPanel.add(safetyPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Connection status
        connectionLabel = new JLabel("Connection: Not connected");
        connectionLabel.setFont(new Font("Arial", Font.BOLD, 13));
        connectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(connectionLabel);
        mainPanel.add(Box.createVerticalStrut(8));

        // Message display
        messageLabel = new JLabel("Welcome to Traffic Light Controller");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(messageLabel);

        add(mainPanel);
    }

    private JLabel createLightLabel(Color lightColor) {

        JLabel light = new JLabel("       ");

        light.setOpaque(true);
        light.setBackground(Color.GRAY);
        light.setPreferredSize(new Dimension(65, 65));
        light.setMaximumSize(new Dimension(65, 65));
        light.setMinimumSize(new Dimension(65, 65));

        light.setBorder(
                BorderFactory.createLineBorder(lightColor, 4)
        );

        light.setAlignmentX(Component.CENTER_ALIGNMENT);

        return light;
    }

    // Update traffic-light display
    public void updateTrafficLights(String currentLight) {

        redLight.setBackground(Color.GRAY);
        yellowLight.setBackground(Color.GRAY);
        greenLight.setBackground(Color.GRAY);

        switch (currentLight) {

            case "RED" ->
                    redLight.setBackground(Color.RED);

            case "YELLOW" ->
                    yellowLight.setBackground(Color.YELLOW);

            case "GREEN" ->
                    greenLight.setBackground(Color.GREEN);
        }
    }

    // Update countdown
    public void updateCountdown(int seconds) {

        countdownLabel.setText(
                "Remaining Time: " + seconds + " seconds"
        );
    }

    // Update mode
    public void updateMode(String mode) {

        modeLabel.setText("Mode: " + mode);
    }

    // Update message
    public void updateMessage(String message) {

        messageLabel.setText(message);
    }

    // Update backend connection status
    public void updateConnectionStatus(boolean connected) {

        if (connected) {

            connectionLabel.setText("Connection: Connected");
            connectionLabel.setForeground(new Color(0, 130, 0));

        } else {

            connectionLabel.setText("Connection: Disconnected");
            connectionLabel.setForeground(Color.RED);
        }
    }

    // Button getters for the controller
    public JButton getStartButton() {
        return startButton;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

    public JButton getResumeButton() {
        return resumeButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getEmergencyButton() {
        return emergencyButton;
    }

    public JButton getManualRedButton() {
        return manualRedButton;
    }

    public JButton getManualYellowButton() {
        return manualYellowButton;
    }

    public JButton getManualGreenButton() {
        return manualGreenButton;
    }
}
