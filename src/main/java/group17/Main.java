package group17;

import group17.Graphics.UserDialogWindow;
import group17.Interfaces.SimulationInterface;

/**
 * Driver class of Program
 */
public class Main {
    /**
     * The constant simulation.
     */
    public static volatile SimulationInterface simulation;
    /**
     * The constant userDialog.
     */
    public static UserDialogWindow userDialog;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        userDialog = new UserDialogWindow();
    }

}
