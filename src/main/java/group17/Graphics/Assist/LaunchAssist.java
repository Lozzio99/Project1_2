package group17.Graphics.Assist;

import javax.swing.*;
import java.awt.*;

public class LaunchAssist extends DialogFrame {

    public LaunchAssist() {
        super();
    }

    public LaunchAssist(Component card) {
        super(card);
    }

    @Override
    public void init() {
        this.setPreferredSize(new Dimension(1000, 500));
        this.setLayout(new GridLayout(1, 2));
        this.add(createSetUpPanel());
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane);
        //this.frame.add(wrapperPanel);
        //this.frame.setVisible(true);
        this.setFocusable(true);
        //this.parentPanel.add(this);
    }

    @Override
    public JPanel createSetUpPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 1));
        inputPanel.add(stepSizePanel());
        inputPanel.add(massPanel());
        inputPanel.add(launchVelocityPanel());
        inputPanel.add(launchPositionPanel());
        inputPanel.add(launchDatePanel());
        inputPanel.add(launchTimePanel());
        inputPanel.add(buttonsPanel());
        return inputPanel;
    }


    public JPanel stepSizePanel() {
        JPanel inputVarPanel1 = new JPanel();
        inputVarPanel1.setLayout(new GridLayout(1, 2));
        inputVarPanel1.add(new JLabel("Time step size "));
        inputVarPanel1.add(stSizeField);
        return inputVarPanel1;
    }

    public JPanel massPanel() {
        JPanel inputVarPanel3 = new JPanel();
        inputVarPanel3.setLayout(new GridLayout(1, 2));
        inputVarPanel3.add(new JLabel("Mass "));
        inputVarPanel3.add(massSizeField);
        return inputVarPanel3;
    }

    public JPanel launchVelocityPanel() {
        JPanel inputVarPanel2 = new JPanel();
        inputVarPanel2.setLayout(new GridLayout(3, 3));
        JLabel xVelLabel = new JLabel("X");
        JLabel yVelLabel = new JLabel("Y");
        JLabel zVelLabel = new JLabel("Z");
        xVelSlider.addChangeListener(new updateLabel(xVelSlider, xVelLabel));
        yVelSlider.addChangeListener(new updateLabel(yVelSlider, yVelLabel));
        zVelSlider.addChangeListener(new updateLabel(zVelSlider, zVelLabel));
        inputVarPanel2.add(new JLabel(" "));
        inputVarPanel2.add(xVelSlider);
        inputVarPanel2.add(xVelLabel);
        inputVarPanel2.add(new JLabel("Launch velocity "));
        inputVarPanel2.add(yVelSlider);
        inputVarPanel2.add(yVelLabel);
        inputVarPanel2.add(new JLabel(" "));
        inputVarPanel2.add(zVelSlider);
        inputVarPanel2.add(zVelLabel);
        return inputVarPanel2;
    }

    public JPanel launchPositionPanel() {
        JPanel inputVectorPanel = new JPanel();
        inputVectorPanel.setLayout(new GridLayout(1, 4));
        inputVectorPanel.add(new JLabel("Launch position "));
        inputVectorPanel.add(lXCoordField);
        inputVectorPanel.add(lYCoordField);
        inputVectorPanel.add(lZCoordField);
        return inputVectorPanel;
    }


    public JPanel launchDatePanel() {
        JPanel inputDatePanel = new JPanel();
        inputDatePanel.setLayout(new GridLayout(1, 4));
        inputDatePanel.add(new JLabel("Date (dd/mm/yyyy) "));
        inputDatePanel.add(ddField);
        inputDatePanel.add(mmField);
        inputDatePanel.add(yyField);
        return (inputDatePanel);
    }

    public JPanel launchTimePanel() {
        JPanel inputTimePanel = new JPanel();
        inputTimePanel.setLayout(new GridLayout(1, 4));
        inputTimePanel.add(new JLabel("Time (hh/mm/ss) "));
        inputTimePanel.add(hhField);
        inputTimePanel.add(mField);
        inputTimePanel.add(ssField);
        return inputTimePanel;
    }


    public JPanel buttonsPanel() {
        JPanel btnPanel = new JPanel();
        JButton startButton = new JButton("Start simulationInstance");
        startButton.addActionListener(new startBtnListener());
        btnPanel.add(startButton);
        JButton clearButton = new JButton("Reset Simulation");
        //to be implemented
        clearButton.addActionListener(new clearBtnListener());
        btnPanel.add(clearButton);
        return btnPanel;
    }

}

