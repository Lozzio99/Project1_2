package group17.phase1.Titan.Simulations.PendulumSimulation;

import group17.phase1.Titan.Physics.Bodies.CelestialBody;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.awt.*;

import static group17.phase1.Titan.Main.simulation;

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
        this.setMASS(10);
        this.setRADIUS(this.getMASS());
        if (id == 1) {
            this.setAngle(130);
            this.setLength(150);
            this.setVectorLocation(new Vector3D(this.getLength() * Math.sin(this.angle), this.getLength() * Math.cos(this.angle), 0));
            this.setVectorVelocity(new Vector3D(0, 0, 0));
            this.setColour(new Color(9, 32, 137, 242));
        } else {
            this.setAngle(60);
            this.setLength(100);
            this.setVectorLocation(new Vector3D((this.getLength() * Math.sin(this.angle)) + simulation.getBody("1").getVectorLocation().getX(),
                    (this.getLength() * Math.cos(this.angle)) + simulation.getBody("1").getVectorLocation().getY(), 0));
            this.setVectorVelocity(new Vector3D(0, 0, 0));
            this.setColour(new Color(167, 4, 4, 223));

        }

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
