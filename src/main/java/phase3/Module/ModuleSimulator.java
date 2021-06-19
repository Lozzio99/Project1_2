package phase3.Module;

import phase3.Math.ADT.Vector3D;

import java.awt.*;

/**
 * The type Rocket simulator.
 */
public class ModuleSimulator extends RocketSimulator {
    @Override
    public void initProperties() {
        this.setMASS(6e3);
        this.setRADIUS(1e1);
        this.setColour(Color.GREEN);
        this.setVectorLocation(new Vector3D(-5e2, 1e3, 0));
        this.setVectorVelocity(new Vector3D(30, 0.0, 0));
        this.fuelMass = this.startFuel;
        this.totalMass = this.startFuel + this.getMASS();
        this.setMASS(this.totalMass);
    }
}
