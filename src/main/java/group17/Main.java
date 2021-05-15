package group17;

import group17.Graphics.Assist.UserDialogWindow;
import group17.Interfaces.SimulationInterface;

public class Main {
    public static volatile SimulationInterface simulation;
    public static UserDialogWindow userDialog;

    public static void main(String[] args) {
        userDialog = new UserDialogWindow();
    }

}
