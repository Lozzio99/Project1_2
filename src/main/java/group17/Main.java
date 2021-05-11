package group17;

import group17.Graphics.Assist.DialogWindow;
import group17.Interfaces.SimulationInterface;

public class Main {
    public static volatile SimulationInterface simulationInstance;
    public static DialogWindow userDialog;

    public static void main(String[] args) {
        userDialog = new DialogWindow();
    }

}
