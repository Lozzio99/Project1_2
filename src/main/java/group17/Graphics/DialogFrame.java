/**
 * This class represents the dialog frame, which is opened upon start.
 *
 * @author Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
 * @version 1.0
 * @since 19/02/2021
 */

package group17.Graphics;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;

import static group17.Config.INSERT_PROBE;
import static group17.Config.STEP_SIZE;
import static group17.Graphics.Scenes.Scene.SceneType.SIMULATION_SCENE;
import static group17.Main.simulationInstance;

/**
 * DialogFrame
 */
public class DialogFrame extends JPanel implements Runnable {

    protected final AtomicReference<Thread> dialogThread = new AtomicReference<Thread>();
    private final JFrame frame;
    private final JTextArea textArea = new JTextArea(10, 30);
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


    // TODO: weight for the velocity slider to be changed if needed
    private final double velocitySliderW = 1.0;


    public DialogFrame() {
        this.frame = new JFrame();
        this.frame.setSize(700, 300);
        this.frame.setTitle("Dialog window");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initialises the frame.
     */
    public void init() {
        JPanel wrapperPanel = this;
        wrapperPanel.setLayout(new GridLayout(1, 2));
        wrapperPanel.add(createSetUpPanel());
        JScrollPane scrollPane = new JScrollPane(textArea);
        wrapperPanel.add(scrollPane);
        this.frame.add(wrapperPanel);
        this.frame.setVisible(true);
        this.frame.setFocusable(true);
    }

    /**
     * Creates the setup panel.
     *
     * @return
     */
    private JPanel createSetUpPanel() {
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
        JButton startButton = new JButton("Start simulationInstance");
        startButton.addActionListener(new startBtnListener());
        btnPanel.add(startButton);
        JButton clearButton = new JButton("Reset Simulation");
        //to be implemented
        clearButton.addActionListener(new clearBtnListener());
        btnPanel.add(clearButton);
        inputPanel.add(btnPanel);
        return inputPanel;
    }


    public void setDate() {
        this.setSsField("" + simulationInstance.getSystem().getClock().getSec());
        this.setMinuteField("" + simulationInstance.getSystem().getClock().getMin());
        this.setHhField("" + simulationInstance.getSystem().getClock().getHour());
        this.setDdField("" + simulationInstance.getSystem().getClock().getDays());
        this.setMonthField("" + simulationInstance.getSystem().getClock().getMonths());
        this.setYyField("" + simulationInstance.getSystem().getClock().getYears());
    }

    public void showAssistParameters() {
        this.setStepField("" + STEP_SIZE);
        this.setDate();
        if (INSERT_PROBE) {
            this.setProbeField("" + simulationInstance.getSystem().getCelestialBodies().get(11).getMASS());
            lXCoordField.setText("" + simulationInstance.getSystem().getCelestialBodies().get(11).getVectorLocation().getX());
            lYCoordField.setText("" + simulationInstance.getSystem().getCelestialBodies().get(11).getVectorLocation().getY());
            lZCoordField.setText("" + simulationInstance.getSystem().getCelestialBodies().get(11).getVectorLocation().getZ());
            String ready = ("READY TO START\n") +
                    ("Those are the starting coordinates : ") +
                    (simulationInstance.getSystem().getCelestialBodies().get(11).getVectorLocation().toString()) +
                    ("\nThis is the starting velocity:\n") +
                    (simulationInstance.getSystem().getCelestialBodies().get(11).getVectorVelocity().toString()) +
                    ("\nIf you want to change the starting velocity\n" +
                            "   > you can increase / decrease the sliders\n") +
                    ("If you want to change the starting position\n" +
                            "   > you can plug in the desired values") +
                    ("If you want to change step size or probe mass\n" +
                            "   > you can plug in the desired value") +
                    ("If you trust our shoot then just START SIMULATION :=)");
            ready += "\n" + simulationInstance.toString() + "\n";
            this.setOutput(ready);
        }
    }

    public void acquireData() {
        STEP_SIZE = getTimeStepSize();
        if (INSERT_PROBE) {
            if (getLaunchVelocityX() != 0)
                simulationInstance.getSystem().getCelestialBodies().get(11).getVectorVelocity().setX(getLaunchVelocityX());
            if (getLaunchVelocityY() != 0)
                simulationInstance.getSystem().getCelestialBodies().get(11).getVectorVelocity().setY(getLaunchVelocityY());
            if (getLaunchVelocityZ() != 0)
                simulationInstance.getSystem().getCelestialBodies().get(11).getVectorVelocity().setZ(getLaunchVelocityZ());
            simulationInstance.getSystem().getCelestialBodies().get(11).setMASS(getProbeMass());
        }
    }


    public void setMonthField(String mmField) {
        this.mmField.setText(mmField);
    }

    public void setMinuteField(String mField) {
        this.mField.setText(mField);
    }

    /**
     * Method to append text to output.
     *
     * @param message
     */
    public void appendToOutput(String message) {
        textArea.append(message + "\n");
    }

    /**
     * Returns the time in seconds.
     *
     * @return
     */
    public double getTimeSS() {
        return Double.parseDouble(ssField.getText());
    }

    /**
     * Sets the output.
     *
     * @param toString
     */
    public void setOutput(String toString) {
        textArea.setText(toString + "\n");
    }

    /**
     * Get the step size.
     *
     * @return
     */
    public double getTimeStepSize() {
        if (stSizeField.getText().equals(""))
            return 0;
        return Double.parseDouble(stSizeField.getText());
    }

    /**
     * Get the mass of a probe.
     *
     * @return
     */
    public double getProbeMass() {
        if (massSizeField.getText().equals(""))
            return 0;
        return Double.parseDouble(massSizeField.getText());
    }

    /**
     * Get the X coordinate of the launch.
     *
     * @return
     */
    public double getLaunchX() {
        if (lXCoordField.getText().equals(""))
            return 0;
        return Double.parseDouble(lXCoordField.getText());
    }

    /**
     * Get the Y coordinate of the launch.
     *
     * @return
     */
    public double getLaunchY() {
        if (lYCoordField.getText().equals(""))
            return 0;
        return Double.parseDouble(lYCoordField.getText());
    }

    /**
     * Get the Z coordinate of the launch.
     *
     * @return
     */
    public double getLaunchZ() {
        if (lZCoordField.getText().equals(""))
            return 0;
        return Double.parseDouble(lZCoordField.getText());
    }

    /**
     * Returns the amount of velocity on the X axis of a probe upon launch.
     *
     * @return
     */
    public double getLaunchVelocityX() {
        return velocitySliderW * xVelSlider.getValue();
    }

    /**
     * Returns the amount of velocity on the Y axis of a probe upon launch.
     *
     * @return
     */
    public double getLaunchVelocityY() {
        return velocitySliderW * yVelSlider.getValue();
    }

    /**
     * Returns the amount of velocity on the Z axis of a probe upon launch.
     *
     * @return
     */
    public double getLaunchVelocityZ() {
        return velocitySliderW * zVelSlider.getValue();
    }

    /**
     * Returns the day of the current date.
     *
     * @return
     */
    public double getDateDD() {
        return Double.parseDouble(ddField.getText());
    }

    /**
     * Returns the month of the current date.
     *
     * @return
     */
    public double getDateMM() {
        return Double.parseDouble(mmField.getText());
    }

    /**
     * Returns the year of the current date.
     *
     * @return
     */
    public double getDateYYYY() {
        return Double.parseDouble(yyField.getText());
    }

    /**
     * Returns the time in hours.
     *
     * @return
     */
    public double getTimeHH() {
        return Double.parseDouble(hhField.getText());
    }

    /**
     * Returns the time in minutes.
     *
     * @return
     */
    public double getTimeMin() {
        return Double.parseDouble(mField.getText());
    }

    public void setDdField(String ddField) {
        this.ddField.setText(ddField);
    }

    public void setYyField(String yyField) {
        this.yyField.setText(yyField);
    }

    public void setHhField(String hhField) {
        this.hhField.setText(hhField);
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

    private static int t = 0;
    @Override
    public void run() {
        if (!simulationInstance.waiting()) {
            this.setDate();
            if (t > 50) {
                this.setOutput(simulationInstance.getSystem().toString());
                t = 0;
            } else t++;
        }
    }

    public void start() {
        this.dialogThread.set(new Thread(this, "Dialog Thread"));
        this.dialogThread.get().setDaemon(true);
        this.dialogThread.get().start();
    }

    /**
     * Nested class representing the update label on the right side of the dialog frame.
     *
     * @author Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
     * @version 1.0
     * @since 19/02/2021
     */
    class updateLabel implements ChangeListener {
        private JLabel jLabel = new JLabel();
        private JSlider jSlider = new JSlider();

        public updateLabel(JSlider jSlider, JLabel jLabel) {
            this.jLabel = jLabel;
            this.jSlider = jSlider;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            jLabel.setText(String.valueOf(velocitySliderW * jSlider.getValue()));
        }

    }

    /**
     * Nested class representing the ActionListener, when the clear button is clicked.
     *
     * @author Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
     * @version 1.0
     * @since 19/02/2021
     */

    class clearBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            simulationInstance.getReporter().report("RESET SIMULATION");
            // TODO : Handle all of this by saying simulationInstance reset/start (then check config there)
            simulationInstance.reset();

        }

    }

    /**
     * Nested class for the ActionListener.
     *
     * @author Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
     * @version 1.0
     * @since 19/02/2021
     */
    class startBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            simulationInstance.getGraphics().changeScene(SIMULATION_SCENE);
            simulationInstance.getReporter().report("START SIMULATION");
            acquireData();
            simulationInstance.setWaiting(false);
        }
    }


}