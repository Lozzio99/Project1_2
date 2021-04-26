package group17.phase1.Titan.Simulations.PendulumSimulation;

import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.awt.*;

public class PendulumBody extends CelestialBody {

    private final int id;
    private double length;
    private double angle;

    public PendulumBody(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pendulum";
    }

    @Override
    public void initProperties() {
        if (id == 1) {
            this.setMASS(60);
            this.setAngle(90);
            this.setLength(150);
            this.setVectorVelocity(new Vector3D(0, 0, 0));
            this.setColour(new Color(9, 32, 137, 242));
        } else {
            this.setMASS(40);
            this.setAngle(-30);
            this.setLength(200);
            this.setVectorVelocity(new Vector3D(0, 0, 0));
            this.setColour(new Color(167, 4, 4, 223));

        }
        this.setRADIUS(this.getMASS() / 4);
    }

    public double getLength() {
        return this.length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
