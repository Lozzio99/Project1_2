

package group17.Graphics.Assist;

import group17.Interfaces.Vector3dInterface;
import group17.Math.Vector3D;
import group17.Simulation.System.Clock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;

import static group17.Graphics.Scenes.Scene.SceneType.SIMULATION_SCENE;
import static group17.Graphics.Scenes.Scene.SceneType.STARTING_SCENE;
import static group17.Main.simulation;
import static group17.Main.userDialog;
import static group17.Utils.Config.*;

/**
 * This class represents the dialog frame, which is opened upon start.
 *
 * @author Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
 * @version 1.0
 * @since 19 /02/2021
 */
public abstract class AbstractLaunchAssist extends JPanel implements Runnable {

    private static int t = 0;
    /**
     * The Dialog thread.
     */
    protected final AtomicReference<Thread> dialogThread = new AtomicReference<>();
    /**
     * The Text area.
     */
    protected final JTextArea textArea = new JTextArea(10, 30);
    /**
     * The Dd field.
     */
    protected final JLabel ddField = new JLabel();
    /**
     * The Mm field.
     */
    protected final JLabel mmField = new JLabel();
    /**
     * The Yy field.
     */
    protected final JLabel yyField = new JLabel();

    /**
     * The Hh field.
     */
    protected final JLabel hhField = new JLabel();
    /**
     * The M field.
     */
    protected final JLabel mField = new JLabel();
    /**
     * The Ss field.
     */
    protected final JLabel ssField = new JLabel();

    protected static boolean overrideExistingVelocity = false;
    /**
     * The Frame.
     */
    protected JFrame frame;
    /**
     * The Output window.
     */
    protected SimulationDataWindow outputWindow;

    /**
     * Instantiates a new Abstract launch assist.
     */
    public AbstractLaunchAssist() {
        //this.setFrame();
    }


    /**
     * Initialises the frame.
     */
    public abstract void init();

    /**
     * Creates the setup panel.
     *
     * @return j panel
     */
    protected abstract JPanel createSetUpPanel();

    /**
     * Sets output window.
     *
     * @param w the w
     */
    public void setOutputWindow(SimulationDataWindow w) {
        this.outputWindow = w;
    }

    /**
     * Sets date.
     */
    public void setDate() {
        this.setSsField("" + simulation.getSystem().getClock().getSec());
        this.setMinuteField("" + simulation.getSystem().getClock().getMin());
        this.setHhField("" + simulation.getSystem().getClock().getHour());
        this.setDdField("" + simulation.getSystem().getClock().getDays());
        this.setMonthField("" + simulation.getSystem().getClock().getMonths());
        this.setYyField("" + simulation.getSystem().getClock().getYears());
    }

    protected final JTextField xVelSlider = new JTextField();
    protected final JTextField yVelSlider = new JTextField();
    protected final JTextField zVelSlider = new JTextField();

    /**
     * Method to append text to output.
     *
     * @param message the message
     */
    public void appendToOutput(String message) {
        textArea.append(message + "\n");
    }


    /**
     * Sets the output.
     *
     * @param toString the to string
     */
    public void setOutput(String toString) {
        textArea.setText(toString + "\n");
    }

    /**
     * Show assist parameters.
     */
    public void showAssistParameters() {
        //this.setStepField("" + STEP_SIZE);
        this.setDate();
        if (INSERT_ROCKET) {
            String ready = ("READY TO START\n") +
                    ("initial mass         :" + simulation.getSystem().getCelestialBodies().get(11).getMASS() + "\n") +
                    ("starting coordinates : ") +
                    (simulation.getSystem().getCelestialBodies().get(11).getVectorLocation().toString()) +
                    ("\nstarting velocity  : ") +
                    (simulation.getSystem().getCelestialBodies().get(11).getVectorVelocity().toString());
            ready += "\n" + simulation.toString() + "\n";
            this.setOutput(ready);
        }
    }

    /**
     * Acquire data.
     */
    public void acquireData() {
        //STEP_SIZE = getTimeStepSize();
        if (INSERT_ROCKET) {
            Vector3dInterface v = new Vector3D(
                    getLaunchVelocityX(),
                    getLaunchVelocityY(),
                    getLaunchVelocityZ());
            if (!v.isZero()) {
                final boolean f = ERROR_EVALUATION;
                ERROR_EVALUATION = false;
                if (overrideExistingVelocity)
                    simulation.getUpdater().getSchedule().setPlan(new Clock().setLaunchDay(), v);
                else
                    simulation.getUpdater().getSchedule().addToPlan(new Clock().setLaunchDay(), v);
                ERROR_EVALUATION = f;
            } else {
                if (overrideExistingVelocity) {
                    simulation.getUpdater().getSchedule().setPlan(new Clock().setLaunchDay(), v);
                    if (REPORT)
                        simulation.getReporter().report("Zero Vector Decision won't be displayed for logical reasons");
                }
            }
        }
    }

    /**
     * Sets month field.
     *
     * @param mmField the mm field
     */
    public void setMonthField(String mmField) {
        this.mmField.setText("      " + mmField);
    }

    /**
     * Sets minute field.
     *
     * @param mField the m field
     */
    public void setMinuteField(String mField) {
        this.mField.setText("       " + mField);
    }

    /**
     * Returns the amount of velocity on the X axis of a probe upon launch.
     *
     * @return launch velocity x
     */
    public double getLaunchVelocityX() {

        return this.xVelSlider.getText().equals("") ? 0 : Double.parseDouble(this.xVelSlider.getText());
    }

    /**
     * Returns the amount of velocity on the Y axis of a probe upon launch.
     *
     * @return launch velocity y
     */
    public double getLaunchVelocityY() {
        return this.yVelSlider.getText().equals("") ? 0 : Double.parseDouble(this.yVelSlider.getText());
    }

    /**
     * Returns the amount of velocity on the Z axis of a probe upon launch.
     *
     * @return launch velocity z
     */
    public double getLaunchVelocityZ() {
        return this.zVelSlider.getText().equals("") ? 0 : Double.parseDouble(this.zVelSlider.getText());
    }


    @Override
    public synchronized void run() {
        if (simulation != null && !simulation.waiting()) {
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

    /**
     * Start.
     */
    public void start() {
        this.dialogThread.set(new Thread(Thread.currentThread().getThreadGroup(), this, "Dialog Thread", 10));
        this.dialogThread.get().setPriority(4);
        this.dialogThread.get().setDaemon(true);
        this.dialogThread.get().start();
    }

    /**
     * Gets frame.
     *
     * @return the frame
     */
    public JFrame getFrame() {
        return this.frame;
    }



    /**
     * Nested class representing the ActionListener, when the clear button is clicked.
     *
     * @author Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
     * @version 1.0
     * @since 19 /02/2021
     */
    class resetBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (simulation.waiting())
                return;
            if (REPORT) simulation.getReporter().report("RESET SIMULATION");
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
     * @since 19 /02/2021
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


    /**
     * The type Pause btn listener.
     */
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

    /**
     * Sets dd field.
     *
     * @param ddField the dd field
     */
    public void setDdField(String ddField) {
        this.ddField.setText("      " + ddField);
    }

    /**
     * Sets yy field.
     *
     * @param yyField the yy field
     */
    public void setYyField(String yyField) {
        this.yyField.setText("  " + yyField);
    }

    /**
     * Sets hh field.
     *
     * @param hhField the hh field
     */
    public void setHhField(String hhField) {
        this.hhField.setText("      " + hhField);
    }

    /**
     * Sets ss field.
     *
     * @param ssField the ss field
     */
    public void setSsField(String ssField) {
        this.ssField.setText("      " + ssField);
    }
}