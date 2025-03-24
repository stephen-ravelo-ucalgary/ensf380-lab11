package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class SmartHomeGUI extends JFrame {
    private SmartHome smartHome;
    private SmartLight smartLight;
    private SmartLock smartLock;
    private SmartThermostat smartThermostat;
    
    // UI Components
    private JLabel lightStatusLabel;
    private JLabel lockStatusLabel;
    private JLabel thermostatStatusLabel;
    private JTextField temperatureField;
    
    public SmartHomeGUI() {
        // Initialize the smart home and devices
        initializeSmartHome();
        
        // Set up the JFrame
        setTitle("Smart Home System");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add title
        JLabel titleLabel = new JLabel("Smart Home System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add device panels
        contentPanel.add(createLightPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        contentPanel.add(createThermostatPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        contentPanel.add(createLockPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        contentPanel.add(createModePanel());
        
        // Add main panel to frame
        add(contentPanel, BorderLayout.CENTER);
        
        // Update status labels to show initial states
        updateStatusLabels();
        
        // Center the window
        setLocationRelativeTo(null);
    }
    
    private void initializeSmartHome() {
        // Create devices
        smartLight = new SmartLight();
        smartLock = new SmartLock();
        smartThermostat = new SmartThermostat();
        
        // Create and build the smart home
        smartHome = new SmartHome()
            .addDevice(smartLight)
            .addDevice(smartLock)
            .addDevice(smartThermostat)
            .build();
    }
    
    private JPanel createLightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Smart Light",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 14)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Status label
        lightStatusLabel = new JLabel("Light: OFF");
        lightStatusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        lightStatusLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lightStatusLabel, BorderLayout.NORTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        // Create buttons with specific size and style
        JButton onButton = createStyledButton("Turn ON");
        JButton offButton = createStyledButton("Turn OFF");
        
        onButton.addActionListener(e -> {
            smartHome.setDeviceState(smartLight, true);
            updateStatusLabels();
            showInfoDialog("Light is now ON");
        });
        
        offButton.addActionListener(e -> {
            smartHome.setDeviceState(smartLight, false);
            updateStatusLabels();
            showInfoDialog("Light is now OFF");
        });
        
        buttonPanel.add(onButton);
        buttonPanel.add(offButton);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createThermostatPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Smart Thermostat",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 14)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Status label
        thermostatStatusLabel = new JLabel("Thermostat set to 20°C");
        thermostatStatusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        thermostatStatusLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(thermostatStatusLabel, BorderLayout.NORTH);
        
        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        // Temperature field
        temperatureField = new JTextField(3);
        temperatureField.setText("20");
        temperatureField.setHorizontalAlignment(JTextField.CENTER);
        
        JButton setTempButton = createStyledButton("Set Thermostat");
        
        setTempButton.addActionListener(e -> {
            try {
                int temperature = Integer.parseInt(temperatureField.getText());
                smartHome.setDeviceState(smartThermostat, temperature);
                updateStatusLabels();
                showInfoDialog("Thermostat set to " + temperature + "°C");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid temperature value.", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        controlPanel.add(temperatureField);
        controlPanel.add(setTempButton);
        panel.add(controlPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createLockPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Smart Lock",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 14)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Status label
        lockStatusLabel = new JLabel("Lock is now LOCKED");
        lockStatusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        lockStatusLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lockStatusLabel, BorderLayout.NORTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton lockButton = createStyledButton("Lock");
        JButton unlockButton = createStyledButton("Unlock");
        
        lockButton.addActionListener(e -> {
            smartHome.setDeviceState(smartLock, true);
            updateStatusLabels();
            showInfoDialog("Lock is now LOCKED");
        });
        
        unlockButton.addActionListener(e -> {
            smartHome.setDeviceState(smartLock, false);
            updateStatusLabels();
            showInfoDialog("Lock is now UNLOCKED");
        });
        
        buttonPanel.add(lockButton);
        buttonPanel.add(unlockButton);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createModePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Modes",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 14)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Mode label
        JLabel modeLabel = new JLabel("Select Mode");
        modeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        modeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(modeLabel, BorderLayout.NORTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton sleepButton = createStyledButton("Sleep Mode");
        JButton vacationButton = createStyledButton("Vacation Mode");
        
        sleepButton.addActionListener(e -> {
            smartHome.sendMessage("Sleep");
            updateStatusLabels();
            showInfoDialog("Light is now OFF");
            showInfoDialog("Thermostat set to 18°C");
            showInfoDialog("Lock is now LOCKED");
            showInfoDialog("Command sent: Sleep");
        });
        
        vacationButton.addActionListener(e -> {
            smartHome.sendMessage("Vacation");
            updateStatusLabels();
            showInfoDialog("Light is now ON");
            showInfoDialog("Thermostat set to 20°C");
            showInfoDialog("Lock is now LOCKED");
            showInfoDialog("Command sent: Vacation");
        });
        
        buttonPanel.add(sleepButton);
        buttonPanel.add(vacationButton);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(145, 30));
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        button.setBackground(new Color(240, 240, 240));
        return button;
    }
    
    /**
     * Shows an information dialog with the given message
     * @param message The message to display
     */
    private void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Smart Home System",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void updateStatusLabels() {
        // Update light status
        String lightStatus = smartLight.getState() ? "ON" : "OFF";
        lightStatusLabel.setText("Light is now " + lightStatus);
        
        // Update lock status
        String lockStatus = smartLock.getState() ? "LOCKED" : "UNLOCKED";
        lockStatusLabel.setText("Lock: " + lockStatus);
        
        // Update thermostat status
        Integer temperature = smartThermostat.getState();
        thermostatStatusLabel.setText("Thermostat: " + temperature + "°C");
    }
    
    // Main method to launch the application
    public static void main(String[] args) {
        try {
            // Set system look and feel for better appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            SmartHomeGUI gui = new SmartHomeGUI();
            gui.setVisible(true);
        });
    }
}