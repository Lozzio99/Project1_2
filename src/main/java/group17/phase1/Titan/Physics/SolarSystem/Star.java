package group17.phase1.Titan.Physics.SolarSystem;

import group17.phase1.Titan.Physics.Vector3D;

public class Star extends CelestialBody
{
    @Override
    public String toString() {
        return "SUN";
    }

    @Override
    public void initPosition() {
        this.setVectorLocation(new Vector3D(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06));
        this.setVectorVelocity(new Vector3D(1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01));
    }
}
