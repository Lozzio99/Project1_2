package group17.Utils;

import group17.Interfaces.ReporterInterface;
import group17.Interfaces.SystemInterface;
import group17.System.Bodies.CelestialBody;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static group17.Main.simulation;
import static group17.Utils.Config.CHECK_COLLISIONS;
import static group17.Utils.Config.REPORT;


public class CollisionDetector {

    private static boolean collision;
    private static String output;


    public static void checkCollided(CelestialBody a, CelestialBody b, double distm) {
        if (!CHECK_COLLISIONS) return;
        collision = false;
        output = "";
        if (distm < (b.getRADIUS() + a.getRADIUS())) {
            if (a.isCollided() || b.isCollided())
                return;
            collision = true;
            output += "[ " + a + " - " + b + " ]";
            if (b.getMASS() < a.getMASS()) {
                b.setCollided(true);
                a.setMASS(a.getMASS() + b.getMASS()); // conservation of mass
                a.setRADIUS(a.getRADIUS() + b.getRADIUS());  //very very stupid
            } else {
                a.setCollided(true);
                b.setMASS(b.getMASS() + a.getMASS());
                b.setRADIUS(b.getRADIUS() + a.getRADIUS());
            }
        }
        if (REPORT && collision)
            simulation.getReporter().report("COLLISION " + output);
    }

    @Contract("_, _ -> param1")
    public static SystemInterface checkCollisions(final @NotNull SystemInterface system, ReporterInterface reporter) {

        collision = false;
        output = "";
        for (int i = 0; i < system.getCelestialBodies().size(); i++) {
            for (int k = 0; k < system.getCelestialBodies().size(); k++) {
                if (i != k) {
                    checkCollided(system.getCelestialBodies().get(i),
                            system.getCelestialBodies().get(k),
                            system.systemState().getPositions().get(i).dist(system.systemState().getPositions().get(k)));

                }
            }
        }
        if (collision) {
            if (REPORT)
                reporter.report("COLLISION " + output);
        }
        return system;
    }


}
