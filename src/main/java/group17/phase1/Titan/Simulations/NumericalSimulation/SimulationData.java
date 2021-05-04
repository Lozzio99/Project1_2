package group17.phase1.Titan.Simulations.NumericalSimulation;

import au.com.bytecode.opencsv.CSVWriter;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.System.Clock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Main.simulation;

public class SimulationData {

    private final List<String[]> raws;

    public SimulationData() {
        this.raws = new ArrayList<>();
        this.raws.add(new String[]{"Starting Simulation parser"});
    }

    public void addRaw(StateInterface state, Clock time) {
        this.raws.add(new String[]{time.toString(), time.monthString()});
        for (int i = 0; i < state.getPositions().size(); i++) {
            this.raws.add(new String[]{
                    simulation.system().getCelestialBodies().get(i).toString(),
                    state.getPositions().get(i).toString(),
                    state.getRateOfChange().getVelocities().get(i).toString()});
        }

    }

    public void printMonth(String directory, int month) {

        File f = new File(this.getClass().getClassLoader().getResource(directory + month + "/data.csv").getFile());
        System.out.println(f.mkdir());
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            CSVWriter cw = new CSVWriter(fw, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            cw.writeAll(this.raws);
            cw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
