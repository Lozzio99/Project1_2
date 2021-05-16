package group17.Graphics.Assist;

import javax.swing.*;
import java.awt.*;

import static group17.Main.simulation;

public class SimulationDataWindow extends JPanel {
    private JTextArea text;

    public SimulationDataWindow() {
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setLayout(new GridLayout(1, 2, 20, 20));
        this.initPanel();
    }

    private void initPanel() {
        JPanel left = new JPanel();
        this.add(left);
        JScrollPane pane = new JScrollPane(this.text = new JTextArea("text"));
        this.add(pane);
    }

    public void init() {
        this.text.setText(simulation.getSystem().toString());
    }

    public void setOutput(String message) {
        this.text.setText(message);
    }
}
