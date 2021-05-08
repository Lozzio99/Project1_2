package group17.System;

import group17.Interfaces.SystemInterface;
import group17.Interfaces.Vector3dInterface;
import group17.System.Bodies.CelestialBody;

public class CollisionDetector {

    private static boolean collision;

    public static void checkCollided(CelestialBody a, Vector3dInterface pa, Vector3dInterface va, CelestialBody b, Vector3dInterface pb, Vector3dInterface vb) {
        Vector3dInterface dist = pb.sub(pa);
        double distm = dist.norm();
        if (distm < b.getRADIUS() + a.getRADIUS()) {
            collision = true;
            if (b.getMASS() < a.getMASS()) {
                b.setCollided(true);
                pb.mark();
                a.setMASS(a.getMASS() + b.getMASS()); // ...
            } else {
                a.setCollided(true);
                pa.mark();
                b.setMASS(b.getMASS() + a.getMASS());
            }
        }
    }

    public static void checkCollisions(SystemInterface system) {
        collision = false;
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
        }
    }

}