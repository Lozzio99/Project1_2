package group17.phase1.Titan.Graphics.user;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DialogFrame
 */
public class DialogFrame extends JFrame {

    private JTextArea textArea = new JTextArea(10, 30);
    private JTextField stSizeField = new JTextField();
    private JTextField massSizeField = new JTextField();
    private JTextField lXCoordField = new JTextField();
    private JTextField lYCoordField = new JTextField();
    private JTextField lZCoordField = new JTextField();

    private JSlider xVelSlider = new JSlider(-100, 100);
    private JSlider yVelSlider = new JSlider(-100, 100);
    private JSlider zVelSlider = new JSlider(-100, 100);

    private static boolean started = false;
    // TODO: weight for the velocity slider to be changed if needed
    private double velocitySliderW = 1.0;

    public void init(){
        setSize(700, 300);
        setTitle("Dialog window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridLayout(1, 2));
        wrapperPanel.add(createSetUpPanel());
        JScrollPane scrollPane = new JScrollPane(textArea);
        wrapperPanel.add(scrollPane);
        add(wrapperPanel);
        setVisible(true);
        pack();
    }

    private JPanel createSetUpPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1));
        
        // Here add all the input parameter fields
        JPanel inputVarPanel1 = new JPanel();
        inputVarPanel1.setLayout(new GridLayout(1, 2));
        inputVarPanel1.add(new JLabel("Time step size "));
        inputVarPanel1.add(stSizeField);
        inputPanel.add(inputVarPanel1);

        JPanel inputVarPanel3 = new JPanel();
        inputVarPanel3.setLayout(new GridLayout(1, 2));

        inputVarPanel3.add(new JLabel("Mass "));
        inputVarPanel3.add(massSizeField);
        inputPanel.add(inputVarPanel3);

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
        inputPanel.add(inputVarPanel2);
        
        JPanel inputVectorPanel = new JPanel();
        inputVectorPanel.setLayout(new GridLayout(1, 4));
        inputVectorPanel.add(new JLabel("Launch position "));

        inputVectorPanel.add(lXCoordField);

        inputVectorPanel.add(lYCoordField);

        inputVectorPanel.add(lZCoordField);
        inputPanel.add(inputVectorPanel);

        JPanel btnPanel = new JPanel();
        JButton startButton = new JButton("Start simulation");
        startButton.addActionListener(new startBtnListener());
        btnPanel.add(startButton);
        JButton clearButton = new JButton("Clear text area");
        clearButton.addActionListener(new clearBtnListener());
        btnPanel.add(clearButton);
        inputPanel.add(btnPanel);

        return inputPanel;
    }

    public boolean isStarted() {
        return started;
    }

    class startBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            started = true;
            System.out.println("Commence simulation...");
        }

    }

    class updateLabel implements ChangeListener {
        private JLabel jLabel = new JLabel();
        private JSlider jSlider = new JSlider();
        public updateLabel(JSlider jSlider, JLabel jLabel) {
            this.jLabel = jLabel;
            this.jSlider = jSlider;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            jLabel.setText(String.valueOf(velocitySliderW*jSlider.getValue()));
        }

    }

    class clearBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
        }

    }

    /**
     * Method to append text to output
     * @param message
     */

    public void appendToOutput(String message) {
        textArea.append(message + "\n");
    }


    public void setOutput(String message) {
        textArea.setText(message);
    }


    public double getTimeStepSize() {
        if (stSizeField.getText().equals(""))
            return 0;
        return Double.parseDouble(stSizeField.getText());
    }

    public double getProbeMass() {
        if (massSizeField.getText().equals(""))
            return 0;
        return Double.parseDouble(massSizeField.getText());
    }

    public double getLaunchX() {
        if (lXCoordField.getText().equals(""))
            return 0;
        return Double.parseDouble(lXCoordField.getText());
    }

    public double getLaunchY() {
        if (lYCoordField.getText().equals(""))
            return 0;
        return Double.parseDouble(lYCoordField.getText());
    }

    public double getLaunchZ() {
        if (lZCoordField.getText().equals(""))
            return 0;
        return Double.parseDouble(lZCoordField.getText());
    }

    public double getLaunchVelocityX() {
        return velocitySliderW*xVelSlider.getValue();
    }

    public double getLaunchVelocityY() {
        return velocitySliderW*yVelSlider.getValue();
    }

    public double getLaunchVelocityZ() {
        return velocitySliderW*zVelSlider.getValue();
    }



    public static void main(String[] args) {
        
        new DialogFrame().init();

    }
}