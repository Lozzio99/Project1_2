package group17.phase1.Titan.Simulations.PendulumSimulation;

import group17.phase1.Titan.Interfaces.ODEFunctionInterface;
import group17.phase1.Titan.Interfaces.ODESolverInterface;
import group17.phase1.Titan.Interfaces.StateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;
import group17.phase1.Titan.System.Clock;

import static group17.phase1.Titan.Main.simulation;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PendulumSolver implements ODESolverInterface {

    double G = 1;
    private ODEFunctionInterface f = (h, y) ->
    {
        PendulumBody a = ((PendulumBody) simulation.system().getCelestialBodies().get(0)), b = ((PendulumBody) simulation.system().getCelestialBodies().get(1));


        double num1 = -G * (2 * a.getMASS() + b.getMASS()) * sin(a.getAngle());
        double num2 = -b.getMASS() * G * sin(a.getAngle() - 2 * b.getAngle());
        double num3 = -2 * sin(a.getAngle() - b.getAngle()) * b.getMASS();
        double num4 = square(b.getVectorVelocity().getX()) * b.getLength() +
                square(a.getVectorVelocity().getX()) * a.getLength() *
                        cos(a.getAngle() - b.getAngle());
        double den = a.getLength() * (2 * a.getMASS() + b.getMASS() - b.getMASS() * cos(2 * a.getAngle() - 2 * b.getAngle()));

        double a1_a = ((num1 + num2 + num3 * num4) / den);

        Vector3dInterface a_1 = new Vector3D(a1_a, 0, 0);

        num1 = 2 * sin(a.getAngle() - b.getAngle());
        num2 = (square(a.getVectorVelocity().getX()) * a.getLength() * (a.getMASS() + b.getMASS()));
        num3 = G * (a.getMASS() + b.getMASS()) * cos(a.getAngle());
        num4 = square(b.getVectorVelocity().getX()) * b.getLength() * b.getMASS() * cos(a.getAngle() - b.getAngle());
        den = b.getLength() * (2 * a.getMASS() + b.getMASS() - (b.getMASS() * cos(2 * a.getAngle() - 2 * b.getAngle())));
        double a2_a = (num1 * (num2 + num3 + num4)) / den;


        Vector3dInterface a_2 = new Vector3D(a2_a, 0, 0);

        y.getRateOfChange().getVelocities().set(0, y.getRateOfChange().getVelocities().get(0).add(a_1));
        y.getRateOfChange().getVelocities().set(1, y.getRateOfChange().getVelocities().get(1).add(a_2));
        return y.getRateOfChange();
    };
    private Clock clock;

    private static double square(double x) {
        return x * x;
    }

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
