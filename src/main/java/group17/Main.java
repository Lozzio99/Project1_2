package group17;

import group17.Graphics.UserDialogWindow;
import group17.Interfaces.SimulationInterface;

/**
 * Driver class of Program
 * The purpose of this class is to initialise the user dialog window
 * which will take care of the initialisation, and allowing
 * the user selecting some initial preferences.
 */
public class Main {

    /**
     * The singleton instance of the simulation.
     * Kept on the Main memory heap to avoid the instance
     * moving around registers and help solving concurrency issues
     * on possible cached values.
     * Improves accessibility from external threads and allows direct
     * reading-writing actions from external monitors without compromising
     * the threading flow.
     * In order to do this, it is necessary to write on the instance whenever
     * there's need to flush updated information and instruction on the main
     * heap, and this will be automatically done from any thread it is
     * writing on the simulation instance.
     * To avoid bad ordering of the passed instructions,it is therefore necessary
     * to contains logical blocks within the instructions updating
     * on the simulation instance.
     * A good advantage of this is the happens-before condition which is
     * implicit in the volatile keyword: meaning all instruction
     * written before of the actual writing on this instance will be
     * kept in the same ordering as they are passed, due to the uniqueness
     * of the information pool, leading to the ability from the developer
     * of forking tasks and then applying them in the correct order.
     */
    public static volatile SimulationInterface simulation;
    /**
     * The instance of the userDialog Window.
     */
    public static UserDialogWindow userDialog = new UserDialogWindow();

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
    }
}
