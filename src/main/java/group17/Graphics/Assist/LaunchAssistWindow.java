package group17.Graphics.Assist;

import javax.swing.*;
import java.awt.*;

/**
 * The type Launch assist.
 */
public class LaunchAssistWindow extends AbstractLaunchAssist {

    /**
     * Instantiates a new Launch assist.
     */
    public LaunchAssistWindow() {
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
        inputPanel.setLayout(new GridLayout(4, 1, 10, 20));
        inputPanel.add(launchVelocityPanel());
        inputPanel.add(launchDatePanel());
        inputPanel.add(launchTimePanel());
        inputPanel.add(buttonsPanel());
        return inputPanel;
    }

    /**
     * Launch date panel j panel.
     *
     * @return the j panel
     */
    public JPanel launchDatePanel() {
        JPanel inputDatePanel = new JPanel();
        inputDatePanel.setLayout(new GridLayout(1, 4, 20, 20));
        inputDatePanel.add(new JLabel("Date (dd/mm/yyyy) "));
        createClockLabels(inputDatePanel, ddField, mmField, yyField);
        return (inputDatePanel);
    }

    /**
     * Launch time panel j panel.
     *
     * @return the j panel
     */
    public JPanel launchTimePanel() {
        JPanel inputTimePanel = new JPanel();
        inputTimePanel.setLayout(new GridLayout(1, 4, 20, 20));
        inputTimePanel.add(new JLabel("Time (hh/mm/ss) "));
        createClockLabels(inputTimePanel, hhField, mField, ssField);
        return inputTimePanel;
    }

    private void createClockLabels(JPanel inputTimePanel, JLabel hhField, JLabel mField, JLabel ssField) {
        hhField.setOpaque(true);
        mField.setOpaque(true);
        ssField.setOpaque(true);
        Font f = hhField.getFont();
        Color background = new Color(0, 0, 0), text = new Color(245, 245, 245);
        hhField.setFont(new Font(f.getName(), Font.PLAIN, 30));
        hhField.setBackground(background);
        hhField.setForeground(text);
        mField.setFont(new Font(f.getName(), Font.PLAIN, 30));
        mField.setBackground(background);
        mField.setForeground(text);
        ssField.setFont(new Font(f.getName(), Font.PLAIN, 30));
        ssField.setBackground(background);
        ssField.setForeground(text);
        inputTimePanel.add(hhField);
        inputTimePanel.add(mField);
        inputTimePanel.add(ssField);
    }


    /**
     * Buttons panel j panel.
     *
     * @return the j panel
     */
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

     /*
     * Step size panel j panel.
     *
     * @return the j panel

    public JPanel stepSizePanel() {
        JPanel inputVarPanel1 = new JPanel();
        inputVarPanel1.setLayout(new GridLayout(1, 2));
        inputVarPanel1.add(new JLabel("Time step size "));
        inputVarPanel1.add(stSizeField);
        return inputVarPanel1;
    }

     * Mass panel j panel.
     *
     * @return the j panel

    public JPanel massPanel() {
        JPanel inputVarPanel3 = new JPanel();
        inputVarPanel3.setLayout(new GridLayout(1, 2));
        inputVarPanel3.add(new JLabel("Mass "));
        inputVarPanel3.add(massSizeField);
        return inputVarPanel3;
    }




     * Launch position panel j panel.
     *
     * @return the j panel

    public JPanel launchPositionPanel() {
        JPanel inputVectorPanel = new JPanel();
        inputVectorPanel.setLayout(new GridLayout(1, 4));
        inputVectorPanel.add(new JLabel("Launch position "));
        inputVectorPanel.add(lXCoordField);
        inputVectorPanel.add(lYCoordField);
        inputVectorPanel.add(lZCoordField);
        return inputVectorPanel;
    }
    */
}

