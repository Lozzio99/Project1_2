package group17.System.Bodies;


import group17.Math.Utils.Vector3D;

import java.awt.*;

public class Star extends CelestialBody {
    @Override
    public String toString() {
        return "SUN";
    }

    @Override
    public void initProperties() {
        this.setMASS(1.988500e+30);
        this.setRADIUS(6.96342e7);//TODO : set this to e8 for collision detector
        this.setColour(Color.yellow);
        this.setVectorLocation(new Vector3D(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06));
        this.setVectorVelocity(new Vector3D(1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01));
    }
}
