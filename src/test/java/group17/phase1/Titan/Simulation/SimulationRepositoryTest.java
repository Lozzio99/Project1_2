package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Interfaces.StateInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationRepositoryTest {

    @Test
    @DisplayName("Solve")
    void Solve() {
        SimulationRepository s = new SimulationRepository();
        double [] ts = new double[1000_000_0];
        double start = 0;
        for (int i =0;i< 100_000_0; i++ )
        {
            ts[i] = start;
            start+= 1000;
        }
        StateInterface[] enough = s.solve(s,s.solarSystemState,ts);
        for (StateInterface e : enough)
            System.out.println(e.toString());
    }
}