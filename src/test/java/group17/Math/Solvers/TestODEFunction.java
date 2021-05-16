package group17.Math.Solvers;

import group17.Interfaces.ODEFunctionInterface;
import group17.Interfaces.RateInterface;
import group17.Interfaces.StateInterface;

public interface TestODEFunction extends ODEFunctionInterface {
    @Override
    RateInterface call(double t, StateInterface y);
}
