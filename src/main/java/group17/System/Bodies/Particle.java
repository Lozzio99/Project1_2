package group17.System.Bodies;

import group17.Math.Vector3D;

import java.awt.*;
import java.util.Random;

public class Particle extends CelestialBody {
    int id;

    public Particle(int id) {
        this.id

                = id;
    }

    @Override
    public String toString() {
        return "PARTICLE" + this.id;
    }

    @Override
    public void initProperties() {
        this.setRADIUS(2e5);
        double x = Math.max(7e8, Math.abs(new Random().nextInt()));
        if (new Random().nextDouble() < 0.5)
            x *= -1;
        double y = Math.max(7e8, Math.abs(new Random().nextInt()));
        if (new Random().nextDouble() < 0.5)
            y *= -1;
        double z = Math.max(7e8, Math.abs(new Random().nextInt()));
        if (new Random().nextDouble() < 0.5)
            z *= -1;

        this.setMASS(10e5);
        this.setVectorLocation(new Vector3D(x, y, z));
        this.setVectorVelocity(new Vector3D(0.1, 0.1, 0.1));
        this.setColour(new Color(97, 255, 52, 215));
    }
}
