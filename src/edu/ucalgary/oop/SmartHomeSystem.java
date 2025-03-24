package edu.ucalgary.oop;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;

public class SmartHomeSystem extends JFrame implements MouseListener {
    
    private String temperature;

    private SmartLight smartLight = new SmartLight();
    private SmartThermostat smartThermostat = new SmartThermostat();
    private SmartLock smartLock = new SmartLock();

    private SmartHome smartHome = new SmartHome()
                .addDevice(smartLight)
                .addDevice(smartThermostat)
                .addDevice(smartLock)
                .build();

    private JLabel title;
    private JLabel lightLabel;
    private JLabel thermostatLabel;
    private JLabel lockLabel;
    private JLabel modeLabel;

    private JLabel lightStatus;
    private JLabel thermostatStatus;
    private JLabel lockStatus;
    private JLabel modePrompt;

    private JButton lightOn;
    private JButton lightOff;
    private JButton setTemperature;
    private JButton lock;
    private JButton unlock;
    private JButton sleep;
    private JButton vacation;

    private JTextField thermostatInput;

    public SmartHomeSystem() {
        super("Smart Home System");
        setupGUI();
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupGUI() {
        title = new JLabel("Smart Home System");
        lightLabel = new JLabel("Smart Light");
        thermostatLabel = new JLabel("Smart Thermostat");
        lockLabel = new JLabel("Smart Lock");
        modeLabel = new JLabel("Modes");
        
        lightStatus = new JLabel("Light: OFF");
        lightOn = new JButton("Turn ON");
        lightOn.addMouseListener(this);
        lightOff = new JButton("Turn OFF");
        lightOff.addMouseListener(this);
        
        thermostatStatus = new JLabel("Thermostat: 20°C");
        thermostatInput = new JTextField("e.g. 21", 15);
        thermostatInput.addMouseListener(this);
        setTemperature = new JButton("Set Temperature");
        setTemperature.addMouseListener(this);

        lockStatus = new JLabel("Lock: LOCKED");
        lock = new JButton("Lock");
        lock.addMouseListener(this);
        unlock = new JButton("Unlock");
        unlock.addMouseListener(this);
        
        modePrompt = new JLabel("Select Mode");
        sleep = new JButton("Sleep Mode");
        sleep.addMouseListener(this);
        vacation = new JButton("Vacation Mode");
        vacation.addMouseListener(this);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new FlowLayout());

        headerPanel.add(title);

        clientPanel.add(lightLabel);
        clientPanel.add(lightStatus);
        clientPanel.add(lightOn);
        clientPanel.add(lightOff);

        clientPanel.add(thermostatLabel);
        clientPanel.add(thermostatStatus);
        clientPanel.add(thermostatInput);
        clientPanel.add(setTemperature);

        clientPanel.add(lockLabel);
        clientPanel.add(lockStatus);
        clientPanel.add(lock);
        clientPanel.add(unlock);

        clientPanel.add(modePrompt);
        clientPanel.add(modeLabel);
        clientPanel.add(sleep);
        clientPanel.add(vacation);        

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(clientPanel, BorderLayout.CENTER);
    }

    public void mouseClicked(MouseEvent event) {
        if(event.getSource().equals(thermostatInput)) {
            thermostatInput.setText("");             
        }
        if(event.getSource().equals(lightOn)) {
            smartHome.setDeviceState(smartLight, true);
            lightStatus.setText("Light is now ON");
            JOptionPane.showMessageDialog(this, "Light is now ON");
        }
        if(event.getSource().equals(lightOff)) {
            smartHome.setDeviceState(smartLight, false);
            lightStatus.setText("Light is now OFF");
            JOptionPane.showMessageDialog(this, "Light is now OFF");
        }
        if(event.getSource().equals(setTemperature)) {
            temperature = thermostatInput.getText();

            boolean validInput = true;
            try {
                Integer.parseInt(temperature);
            }
            catch( Exception e ) {
                validInput = false;
            }

            if (validInput) {
                smartHome.setDeviceState(smartThermostat, Integer.parseInt(temperature));
                thermostatStatus.setText("Thermostat set to " + temperature + "°C");
                JOptionPane.showMessageDialog(this, "Thermostat set to " + temperature + "°C");
            }
        }
        if(event.getSource().equals(lock)) {
            smartHome.setDeviceState(smartLock, true);
            lockStatus.setText("Lock is now LOCKED");
            JOptionPane.showMessageDialog(this, "Lock is now LOCKED");
        }
        if(event.getSource().equals(unlock)) {
            smartHome.setDeviceState(smartLock, false);
            lockStatus.setText("Lock is now UNLOCKED");
        }
        if(event.getSource().equals(sleep)) {
            smartHome.sendMessage("Sleep");
            lightStatus.setText("Light is now OFF");
            thermostatStatus.setText("Thermostat set to 18°C");
            lockStatus.setText("Lock is now LOCKED");
            JOptionPane.showMessageDialog(this, "Light is now OFF");
            JOptionPane.showMessageDialog(this, "Thermostat set to 18°C");
            JOptionPane.showMessageDialog(this, "Lock is now LOCKED");
        }
        if(event.getSource().equals(vacation)) {
            smartHome.sendMessage("Vacation");
            lightStatus.setText("Light is now " + (smartLight.getState() ? "ON" : "OFF"));
            thermostatStatus.setText("Thermostat set to 20°C");
            lockStatus.setText("Lock is now LOCKED");
            JOptionPane.showMessageDialog(this, "Light is now " + (smartLight.getState() ? "ON" : "OFF"));
            JOptionPane.showMessageDialog(this, "Thermostat set to 20°C");
            JOptionPane.showMessageDialog(this, "Lock is now LOCKED");
        }
    }
    
    public void mouseEntered(MouseEvent event){
        
    }

    public void mouseExited(MouseEvent event){
        
    }

    public void mousePressed(MouseEvent event){
        
    }

    public void mouseReleased(MouseEvent event){
        
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new SmartHomeSystem().setVisible(true);        
        });
    }
}
