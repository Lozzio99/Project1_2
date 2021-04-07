/**
 * This class represents the dialog frame, which is opened upon start.
 * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
 * @version 1.0
 * @since	19/02/2021
 */

package group17.phase1.Titan.Graphics;

import group17.phase1.Titan.Graphics.Scenes.Scene;
import group17.phase1.Titan.Main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static group17.phase1.Titan.Main.simulation;
import static group17.phase1.Titan.Physics.Clock.*;



/**
 * DialogFrame
 */
public class DialogFrame extends JPanel
{

    private final JFrame frame;
    private final JTextArea textArea = new JTextArea(10,30);
    private final JTextField stSizeField = new JTextField();
    private final JTextField massSizeField = new JTextField();

    private final JTextField lXCoordField = new JTextField();
    private final JTextField lYCoordField = new JTextField();
    private final JTextField lZCoordField = new JTextField();

    private final JTextField ddField = new JTextField();
    private final JTextField mmField = new JTextField();
    private final JTextField yyField = new JTextField();

    private final JTextField hhField = new JTextField();
    private final JTextField mField = new JTextField();
    private final JTextField ssField = new JTextField();

    private final JSlider xVelSlider = new JSlider(-30000, 30000);
    private final JSlider yVelSlider = new JSlider(-30000, 30000);
    private final JSlider zVelSlider = new JSlider(-30000, 30000);

    private static boolean stopped = true;

    // TODO: weight for the velocity slider to be changed if needed
    private final double velocitySliderW = 1.0;


    public DialogFrame()
    {
        this.frame = new JFrame();
        this.frame.setSize(700, 300);
        this.frame.setTitle("Dialog window");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.init();
        this.frame.setVisible(true);
        this.frame.setFocusable(true);
    }

    /**
     * Initialises the frame.
     */
    public void init()
    {
        JPanel wrapperPanel  = this;
        wrapperPanel.setLayout(new GridLayout(1, 2));
        wrapperPanel.add(createSetUpPanel());
        JScrollPane scrollPane = new JScrollPane(textArea);
        wrapperPanel.add(scrollPane);
        this.frame.add(wrapperPanel);
    }

    /**
     * Creates the setup panel.
     * @return
     */
    private JPanel createSetUpPanel()
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 1));

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

        JPanel inputDatePanel = new JPanel();
        inputDatePanel.setLayout(new GridLayout(1, 4));
        inputDatePanel.add(new JLabel("Date (dd/mm/yyyy) "));
        inputDatePanel.add(ddField);
        inputDatePanel.add(mmField);
        inputDatePanel.add(yyField);
        inputPanel.add(inputDatePanel);

        JPanel inputTimePanel = new JPanel();
        inputTimePanel.setLayout(new GridLayout(1, 4));
        inputTimePanel.add(new JLabel("Time (hh/mm/ss) "));
        inputTimePanel.add(hhField);
        inputTimePanel.add(mField);
        inputTimePanel.add(ssField);
        inputPanel.add(inputTimePanel);

        JPanel btnPanel = new JPanel();
        JButton startButton = new JButton("Start simulation");
        startButton.addActionListener(new startBtnListener());
        btnPanel.add(startButton);
        JButton clearButton = new JButton("Reset Simulation");
        //to be implemented
        clearButton.addActionListener(new clearBtnListener());
        btnPanel.add(clearButton);
        inputPanel.add(btnPanel);
        return inputPanel;
    }

    public boolean isStopped() {
        return stopped;
    }


    public void setDate(int s, int m, int h, int d, int mo, int ye)
    {
        this.setSsField(""+s);
        this.setmField(""+m);
        this.setHhField(""+h);
        this.setDdField(""+d);
        this.setMmField(""+mo);
        this.setYyField(""+ye);
    }

    public void showAssistParameters()
    {
        this.setProbeField(""+simulation.getBody("PROBE").getMASS());
        this.setStepField(""+simulation.getStepSize());

        this.setDdField(""+ dd);
        this.setMmField(""+mm);
        this.setYyField(""+yy);
        this.setHhField(""+hour);
        this.setmField(""+min);
        this.setSsField(""+sec);

        lXCoordField.setText(""+ Main.simulation.getBody("PROBE").getVectorLocation().getX());
        lYCoordField.setText(""+Main.simulation.getBody("PROBE").getVectorLocation().getY());
        lZCoordField.setText(""+Main.simulation.getBody("PROBE").getVectorLocation().getZ());

        String ready = ("READY TO START\n") +
        ("Those are the starting coordinates : ")+
        (simulation.solarSystem().getCelestialBodies().get(11).getVectorLocation().toString())+
        ("\nThis is the starting velocity:\n")+
        (simulation.solarSystem().getCelestialBodies().get(11).getVectorVelocity().toString())+
        ("\nIf you want to change the starting velocity\n" +
                "   > you can increase / decrease the sliders\n")+
        ("If you want to change the starting position\n" +
                "   > you can plug in the desired values")+
        ("If you want to change step size or probe mass\n" +
                "   > you can plug in the desired value")+
        ("If you trust our shoot then just START SIMULATION :=)");

        this.setOutput(ready);
    }


    public void acquireData()
    {
        if (getLaunchVelocityX()!= 0)
            simulation.getBody("PROBE").getVectorVelocity().setX(getLaunchVelocityX());
        if (getLaunchVelocityY()!= 0)
            simulation.getBody("PROBE").getVectorVelocity().setY(getLaunchVelocityY());
        if (getLaunchVelocityZ()!= 0)
            simulation.getBody("PROBE").getVectorVelocity().setZ(getLaunchVelocityZ());
        simulation.setStepSize(getTimeStepSize()) ;
        simulation.getBody("PROBE").setMASS(getProbeMass());
    }




    /**
     * Nested class representing the update label on the right side of the dialog frame.
     * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
     * @version 1.0
     * @since	19/02/2021
     */
    class updateLabel implements ChangeListener
    {
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

    /**
     * Nested class representing the ActionListener, when the clear button is clicked.
     * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
     * @version 1.0
     * @since	19/02/2021
     */

    class clearBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            stopped= true;
            simulation.graphics().changeScene(Scene.SceneType.STARTING_SCENE);
            simulation.reset();
            System.out.println("Reset simulation...");
        }

    }
    /**
     * Nested class for the ActionListener.
     * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
     * @version 1.0
     * @since	19/02/2021
     */
    class startBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            stopped= false;
            acquireData();
            simulation.graphics().changeScene(Scene.SceneType.TRAJECTORIES);
            System.out.println("Commence simulation...");
        }

    }
    /**
     * Method to append text to output.
     * @param message
     */
    public void appendToOutput(String message)
    {
        textArea.append(message + "\n");
    }

    /**
     * Sets the output.
     * @param toString
     */
    public void setOutput(String toString)
    {
        textArea.setText(toString + "\n");
    }

    /**
     * Get the step size.
     * @return
     */
    public double getTimeStepSize()
    {
        if (stSizeField.getText().equals(""))
            return 0;
        return Double.parseDouble(stSizeField.getText());
    }

    /**
     * Get the mass of a probe.
     * @return
     */
    public double getProbeMass()
    {
        if (massSizeField.getText().equals(""))
            return 0;
        return Double.parseDouble(massSizeField.getText());
    }

    /**
     * Get the X coordinate of the launch.
     * @return
     */
    public double getLaunchX()
    {
        if (lXCoordField.getText().equals(""))
            return 0;
        return Double.parseDouble(lXCoordField.getText());
    }

    /**
     * Get the Y coordinate of the launch.
     * @return
     */
    public double getLaunchY()
    {
        if (lYCoordField.getText().equals(""))
            return 0;
        return Double.parseDouble(lYCoordField.getText());
    }

    /**
     * Get the Z coordinate of the launch.
     * @return
     */
    public double getLaunchZ()
    {
        if (lZCoordField.getText().equals(""))
            return 0;
        return Double.parseDouble(lZCoordField.getText());
    }

    /**
     * Returns the amount of velocity on the X axis of a probe upon launch.
     * @return
     */
    public double getLaunchVelocityX()
    {
        return velocitySliderW*xVelSlider.getValue();
    }

    /**
     * Returns the amount of velocity on the Y axis of a probe upon launch.
     * @return
     */
    public double getLaunchVelocityY()
    {
        return velocitySliderW*yVelSlider.getValue();
    }

    /**
     * Returns the amount of velocity on the Z axis of a probe upon launch.
     * @return
     */
    public double getLaunchVelocityZ()
    {
        return velocitySliderW*zVelSlider.getValue();
    }

    /**
     * Returns the day of the current date.
     * @return
     */
    public double getDateDD()
    {
        return Double.parseDouble(ddField.getText());
    }

    /**
     * Returns the month of the current date.
     * @return
     */
    public double getDateMM()
    {
        return Double.parseDouble(mmField.getText());
    }

    /**
     * Returns the year of the current date.
     * @return
     */
    public double getDateYYYY()
    {
        return Double.parseDouble(yyField.getText());
    }

    /**
     * Returns the time in hours.
     * @return
     */
    public double getTimeHH()
    {
        return Double.parseDouble(hhField.getText());
    }

    /**
     * Returns the time in minutes.
     * @return
     */
    public double getTimeMin()
    {
        return Double.parseDouble(mField.getText());
    }

    /**
     * Returns the time in seconds.
     * @return
     */
    public double getTimeSS()
    {
        return Double.parseDouble(ssField.getText());
    }


    public void setDdField(String ddField) {
        this.ddField.setText(ddField);
    }

    public void setMmField(String mmField) {
        this.mmField.setText(mmField);
    }

    public void setYyField(String yyField) {
        this.yyField.setText(yyField);
    }

    public void setHhField(String hhField) {
        this.hhField.setText(hhField);
    }

    public void setmField(String mField) {
        this.mField.setText(mField);
    }

    public void setSsField(String ssField) {
        this.ssField.setText(ssField);
    }

    public void setStepField(String ssField) {
        this.stSizeField.setText(ssField);
    }

    public void setProbeField(String ssField) {
        this.massSizeField.setText(ssField);
    }



}