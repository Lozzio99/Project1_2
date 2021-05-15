package group17.Graphics.Assist;

import javax.swing.*;
import java.awt.*;

public class LaunchAssist extends AbstractLaunchAssist {

    public LaunchAssist() {
        super();
    }


    @Override
    public void init() {
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setPreferredSize(new Dimension(1000, 500));
        this.setLayout(new GridLayout(1, 2, 20, 20));
        this.add(createSetUpPanel());
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane);
        this.setFocusable(true);
        this.outputWindow.init();
    }

    @Override
    public JPanel createSetUpPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(101, 101, 101, 226));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setLayout(new GridLayout(7, 1, 10, 20));
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
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnPanel.setLayout(new GridLayout(1, 3, 30, 0));
        JButton startButton = new JButton("LAUNCH");
        startButton.addActionListener(new startBtnListener());
        btnPanel.add(startButton);
        JButton clearButton = new JButton("RESET");
        clearButton.addActionListener(new resetBtnListener());
        btnPanel.add(clearButton);
        JButton pauseButton = new JButton("PAUSE");
        pauseButton.addActionListener(new pauseBtnListener());
        btnPanel.add(pauseButton);
        return btnPanel;
    }

}

