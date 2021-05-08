package group17.System;

import group17.Interfaces.ReporterInterface;
import group17.Interfaces.SystemInterface;
import group17.Interfaces.Vector3dInterface;
import group17.System.Bodies.CelestialBody;

import static group17.Config.REPORT;

public class CollisionDetector {

    private static boolean collision;
    private static String output;

    public static void checkCollided(CelestialBody a, Vector3dInterface pa, Vector3dInterface va, CelestialBody b, Vector3dInterface pb, Vector3dInterface vb) {
        Vector3dInterface dist = pb.sub(pa);
        double distm = dist.norm();
        if (distm < (b.getRADIUS() + a.getRADIUS())) {
            System.out.println(distm + " should be less than " + (b.getRADIUS() + a.getRADIUS()));
            collision = true;
            output += "[ " + a + " - " + b + " ]";
            if (b.getMASS() < a.getMASS()) {
                b.setCollided(true);
                pb.mark();
                vb.mark();
                a.setMASS(a.getMASS() + b.getMASS()); // conservation of mass
                a.setRADIUS(a.getRADIUS() + b.getRADIUS());  //very very stupid
            } else {
                a.setCollided(true);
                pa.mark();
                va.mark();
                b.setMASS(b.getMASS() + a.getMASS());
                b.setRADIUS(b.getRADIUS() + a.getRADIUS());
            }
        }
    }

    public static void checkCollisions(SystemInterface system, ReporterInterface reporter) {
        collision = false;
        output = "";
        for (int i = 0; i < system.getCelestialBodies().size(); i++) {
            for (int k = 0; k < system.getCelestialBodies().size(); k++) {
                if (i != k) {
                    checkCollided(system.getCelestialBodies().get(i),
                            system.systemState().getPositions().get(i),
                            system.systemState().getRateOfChange().getVelocities().get(i),
                            system.getCelestialBodies().get(k),
                            system.systemState().getPositions().get(k),
                            system.systemState().getRateOfChange().getVelocities().get(k));
                }
            }
        }
        if (collision) {
            system.getCelestialBodies().removeIf(CelestialBody::isCollided);
            system.systemState().getPositions().removeIf(Vector3dInterface::isMarked);
            system.systemState().getRateOfChange().getVelocities().removeIf(Vector3dInterface::isMarked);

            if (REPORT)
                reporter.report("COLLISION " + output);
        }
    }

}
