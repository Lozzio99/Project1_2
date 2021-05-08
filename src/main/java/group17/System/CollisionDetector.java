package group17.System;

import group17.Interfaces.SystemInterface;
import group17.Interfaces.Vector3dInterface;
import group17.System.Bodies.CelestialBody;

public class CollisionDetector {

    public static void checkCollided(CelestialBody a, Vector3dInterface pa, CelestialBody b, Vector3dInterface pb) {
        Vector3dInterface dist = pb.sub(pa);
        double distm = dist.norm();
        if (distm < b.getRADIUS() + a.getRADIUS()) {
            if (b.getMASS() < a.getMASS()) {
                b.setCollided(true);
                a.setMASS(a.getMASS() + b.getMASS()); // ...
            } else {
                a.setCollided(true);
                b.setMASS(b.getMASS() + a.getMASS());
            }
        }
    }

    public static void checkCollisions(SystemInterface system) {
        for (int i = 0; i < system.getCelestialBodies().size(); i++) {
            for (int k = 0; k < system.getCelestialBodies().size(); k++) {
                if (i != k) {
                    checkCollided(system.getCelestialBodies().get(i),
                            system.systemState().getPositions().get(i),
                            system.getCelestialBodies().get(k),
                            system.systemState().getPositions().get(k));
                }
            }
        }
        system.getCelestialBodies().removeIf(CelestialBody::isCollided);
    }

}
