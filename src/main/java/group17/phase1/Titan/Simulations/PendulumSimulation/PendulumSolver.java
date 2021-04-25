package group17.phase1.Titan.Simulations.PendulumSimulation;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.System.Clock;

public class PendulumSolver implements ODESolverInterface {
    private ODEFunctionInterface f = (t, y) -> y.getRateOfChange();
    private Clock clock;

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        getClock().step(h);
        return y.addMul(h, f.call(h, y));
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this.f;
    }

    @Override
    public void setF(ODEFunctionInterface f) {
        this.f = f;
    }

    @Override
    public Clock getClock() {
        return this.clock;
    }

    @Override
    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
