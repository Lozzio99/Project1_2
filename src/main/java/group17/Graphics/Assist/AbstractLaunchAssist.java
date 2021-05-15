/**
 * This class represents the dialog frame, which is opened upon start.
 *
 * @author Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
 * @version 1.0
 * @since 19/02/2021
 */

package group17.Graphics.Assist;

import group17.Interfaces.Vector3dInterface;
import group17.Math.Utils.Vector3D;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;

import static group17.Config.*;
import static group17.Graphics.Scenes.Scene.SceneType.SIMULATION_SCENE;
import static group17.Graphics.Scenes.Scene.SceneType.STARTING_SCENE;
import static group17.Main.simulation;
import static group17.Main.userDialog;

/**
 * DialogFrame
 */
public abstract class AbstractLaunchAssist extends JPanel implements Runnable {

    private static int t = 0;
    protected final AtomicReference<Thread> dialogThread = new AtomicReference<Thread>();
    protected final JTextArea textArea = new JTextArea(10, 30);
    protected final JTextField stSizeField = new JTextField();
    protected final JTextField massSizeField = new JTextField();

    protected final JTextField lXCoordField = new JTextField();
    protected final JTextField lYCoordField = new JTextField();
    protected final JTextField lZCoordField = new JTextField();

    protected final JTextField ddField = new JTextField();
    protected final JTextField mmField = new JTextField();
    protected final JTextField yyField = new JTextField();

    protected final JTextField hhField = new JTextField();
    protected final JTextField mField = new JTextField();
    protected final JTextField ssField = new JTextField();
    protected final JSlider xVelSlider = new JSlider(-30000, 30000);
    protected final JSlider yVelSlider = new JSlider(-30000, 30000);
    protected final JSlider zVelSlider = new JSlider(-30000, 30000);
    // TODO: weight for the velocity slider to be changed if needed
    private final double velocitySliderW = 1.0;
    protected JFrame frame;
    protected SimulationDataWindow outputWindow;

    public AbstractLaunchAssist() {
        //this.setFrame();
    }

    private void setFrame() {
        this.frame = new JFrame();
        this.frame.setSize(700, 300);
        this.frame.setTitle("Dialog window");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initialises the frame.
     */
    public abstract void init();

    /**
     * Creates the setup panel.
     *
     * @return
     */
    protected abstract JPanel createSetUpPanel();

    public void setOutputWindow(SimulationDataWindow w) {
        this.outputWindow = w;
    }

    public void setDate() {
        this.setSsField("" + simulation.getSystem().getClock().getSec());
        this.setMinuteField("" + simulation.getSystem().getClock().getMin());
        this.setHhField("" + simulation.getSystem().getClock().getHour());
        this.setDdField("" + simulation.getSystem().getClock().getDays());
        this.setMonthField("" + simulation.getSystem().getClock().getMonths());
        this.setYyField("" + simulation.getSystem().getClock().getYears());
    }

    public void showAssistParameters() {
        this.setStepField("" + STEP_SIZE);
        this.setDate();
        if (INSERT_ROCKET) {
            this.setProbeField("" + simulation.getSystem().getCelestialBodies().get(11).getMASS());
            lXCoordField.setText("" + simulation.getSystem().getCelestialBodies().get(11).getVectorLocation().getX());
            lYCoordField.setText("" + simulation.getSystem().getCelestialBodies().get(11).getVectorLocation().getY());
            lZCoordField.setText("" + simulation.getSystem().getCelestialBodies().get(11).getVectorLocation().getZ());
            String ready = ("READY TO START\n") +
                    ("Those are the starting coordinates : ") +
                    (simulation.getSystem().getCelestialBodies().get(11).getVectorLocation().toString()) +
                    ("\nThis is the starting velocity:\n") +
                    (simulation.getSystem().getCelestialBodies().get(11).getVectorVelocity().toString()) +
                    ("\nIf you want to change the starting velocity\n" +
                            "   > you can increase / decrease the sliders\n") +
                    ("If you want to change the starting position\n" +
                            "   > you can plug in the desired values") +
                    ("If you want to change step size or probe mass\n" +
                            "   > you can plug in the desired value") +
                    ("If you trust our shoot then just START SIMULATION :=)");
            ready += "\n" + simulation.toString() + "\n";
            this.setOutput(ready);
        }
    }

    public void acquireData() {
        STEP_SIZE = getTimeStepSize();
        if (INSERT_ROCKET && !simulation.getSystem().getRocket().isCollided()) {
            Vector3dInterface v = new Vector3D(getLaunchVelocityX(), getLaunchVelocityY(), getLaunchVelocityZ());
            if (!v.isZero()) {
                simulation.getUpdater().getSchedule().addToPlan(LAUNCH_DATE, v);
                System.out.println(v);
            }
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

    @Override
    public synchronized void run() {
        if (!simulation.waiting()) {
            this.setDate();
            if (t > 50) {
                try {
                    this.outputWindow.setOutput(simulation.getSystem().toString());
                } catch (NullPointerException | IndexOutOfBoundsException ignored) {
                }
                t = 0;
            } else t++;
        }
    }

    public void start() {
        this.dialogThread.set(new Thread(Thread.currentThread().getThreadGroup(), this, "Dialog Thread", 10));
        this.dialogThread.get().setPriority(4);
        this.dialogThread.get().setDaemon(true);
        this.dialogThread.get().start();
    }

    public JFrame getFrame() {
        return this.frame;
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

    class resetBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (simulation.waiting())
                return;
            if (REPORT)
                simulation.getReporter().report("RESET SIMULATION");
            if (ENABLE_GRAPHICS)
                simulation.getGraphics().changeScene(STARTING_SCENE);
            if (LAUNCH_ASSIST)
                userDialog.getMainPane().setSelectedIndex(1);
            simulation.reset();
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
            if (!simulation.waiting()) return;
            if (ENABLE_GRAPHICS) {
                simulation.getGraphics().changeScene(SIMULATION_SCENE);
                simulation.getGraphics().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
            if (REPORT) simulation.getReporter().report("START SIMULATION");
            acquireData();
            new Thread(() -> {       // wait 3 sec without stopping AWT event queue
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    if (REPORT) simulation.getReporter().report(Thread.currentThread(), ex);
                }
                if (ENABLE_GRAPHICS)
                    simulation.getGraphics().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                simulation.setWaiting(false);
            }, "CountDown").start();
        }
    }


    class pauseBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!simulation.waiting()) {
                ((JButton) e.getSource()).setText("RESUME");
                simulation.setWaiting(true);
            } else {
                ((JButton) e.getSource()).setText("PAUSE");
                simulation.setWaiting(false);
            }
        }
    }


}