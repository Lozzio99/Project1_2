package group17.System.Bodies;

import group17.Math.Utils.Vector3D;

import java.awt.*;

public class BlackHole extends CelestialBody {

    @Override
    public String toString() {
        return "M86";
    }

    @Override
    public void initProperties() {
        this.setMASS(2e30); //would be nice to push this higher
        this.setRADIUS(7e5);
        this.setColour(new Color(255, 0, 0, 149));
        this.setVectorLocation(new Vector3D(0.2, -0.2, 0.2));
        this.setVectorVelocity(new Vector3D(0.1, 0.1, 0.1));
    }
}
