package group17.phase1.Titan.Graphics;

import group17.phase1.Titan.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DialogFrame
 */
public class DialogFrame extends JFrame {

    private JTextArea textArea;
    private JTextField stSizeField = new JTextField(2);

    private JTextField lXCoordField = new JTextField(2);

    private JTextField lYCoordField = new JTextField(2);

    private JTextField lZCoordField = new JTextField(2);

    private double X_LAUNCH,Y_LAUNCH,Z_LAUNCH;
    private boolean started = false;

    public void init()
    {
        setSize(800, 300);
        setTitle("Dialog window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridLayout(1, 2, 1, 1));

        wrapperPanel.add(createSetUpPanel());
        this.textArea = new JTextArea(10, 30);

        JScrollPane scrollPane = new JScrollPane(this.textArea);
        wrapperPanel.add(scrollPane);

        add(wrapperPanel);
        setVisible(true);
    }

    private JPanel createSetUpPanel() {
        JPanel rootPanel = new JPanel();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 5, 5));
        
        // Here add all the input parameter fields
        inputPanel.add(new JLabel("Time step size (sec.) "));
        inputPanel.add(stSizeField);

        inputPanel.add(new JLabel("Launch X "));
        inputPanel.add(lXCoordField);

        inputPanel.add(new JLabel("Launch Y "));
        inputPanel.add(lYCoordField);

        inputPanel.add(new JLabel("Launch Z "));
        inputPanel.add(lZCoordField);

        JButton startButton = new JButton("Start simulation");
        startButton.addActionListener(new startBtnListener());
        inputPanel.add(startButton);

        JButton clearButton = new JButton("Clear text area");
        clearButton.addActionListener(new clearBtnListener());
        inputPanel.add(clearButton);

        rootPanel.add(inputPanel);
        return rootPanel;
    }

    class startBtnListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            started = true;
            System.out.println("Commence simulation...");
        }

    }

    public boolean isStarted() {
        return started;
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
        textArea.setText(message + "\n");
    }

    public double getTimeStepSize() {
        return Double.parseDouble(stSizeField.getText());
    }

    public double getLaunchX()
    {
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

}