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

    double G = 3.4;
    double damp = 0.9;
    private ODEFunctionInterface f = (h, y) ->
    {

        PendulumBody a = ((PendulumBody) simulation.system().getCelestialBodies().get(0)), b = ((PendulumBody) simulation.system().getCelestialBodies().get(1));

        double a1 = y.getPositions().get(0).getY(), m1 = a.getMASS(), a1_v = y.getPositions().get(0).getX(), r1 = a.getLength();
        double a2 = y.getPositions().get(1).getY(), m2 = b.getMASS(), a2_v = y.getPositions().get(1).getX(), r2 = b.getLength();


        double num1 = -G * (2 * m1 + m2) * sin(a1);
        double num2 = -m2 * G * sin(a1 - 2 * a2);
        double num3 = -2 * sin(a1 - a2) * m2;
        double num4 = square(a2_v) * r2 + square(a1_v) * r1 * cos(a1 - a2);
        double den = r1 * (2 * m1 + m2 - m2 * cos(2 * a1 - 2 * a2));

        double a1_a = (num1 + num2 + num3 * num4) / den;


        num1 = 2 * sin(a1 - a2);
        num2 = square(a1_v) * r1 * (m1 + m2);
        num3 = G * (m1 + m2) * cos(a1);
        num4 = square(a2_v) * r2 * m2 * cos(a1 - a2);
        den = r2 * (2 * m1 + m2 - m2 * cos(2 * a1 - 2 * a2));

        double a2_a = (num1 * (num2 + num3 + num4)) / den;

        Vector3dInterface a_1 = new Vector3D(a1_a, a1_v, 0);
        Vector3dInterface a_2 = new Vector3D(a2_a, a2_v, 0);


        // velocity += acceleration
        // angle += velocity


        //state has velocity, angle
        //rate has angle acceleration , angle velocity,0


        y.getRateOfChange().getVelocities().set(0, (a_1));
        y.getRateOfChange().getVelocities().set(1, (a_2));
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
