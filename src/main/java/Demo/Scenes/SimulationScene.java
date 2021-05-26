package Demo.Scenes;


import group17.Math.Lib.Point3D;
import group17.Math.Lib.Point3DConverter;
import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Arrays;

import static group17.Main.simulation;
import static group17.Utils.Config.*;

/**
 * The type Simulation scene.
 */
public class SimulationScene extends Scene {
    /**
     * The Planets positions.
     */
    volatile Point3D[] planetsPositions;
    /**
     * The Radius.
     */
    volatile double[] radius;
    /**
     * The Trajectories.
     */
    volatile Bag[] trajectories;


    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);
        try {
            for (int i = 0; i < this.planetsPositions.length; i++) {
                final Point p = Point3DConverter.convertPoint(this.planetsPositions[i]);
                if (NAMES) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g.drawString(simulation.getSystem().getCelestialBodies().get(i).toString(), p.x, p.y);
                }
                g.setColor(simulation.getSystem().getCelestialBodies().get(i).getColour());
                g.fill(planetShape(this.planetsPositions[i], this.radius[i]));
                if (DRAW_TRAJECTORIES) {
                    for (int k = this.trajectories[i].insert; k < this.trajectories[i].getTrajectories().length - 1; k++) {
                        if (this.trajectories[i].getTrajectories()[k + 1] == null)
                            break;
                        g.draw(new Line2D.Double(
                                Point3DConverter.convertPoint(this.trajectories[i].getTrajectories()[k]),
                                Point3DConverter.convertPoint(this.trajectories[i].getTrajectories()[k + 1])));
                    }
                    for (int k = 0; k < this.trajectories[i].insert - 1; k++) {
                        if (this.trajectories[i].getTrajectories()[k + 1] == null)
                            break;
                        g.draw(new Line2D.Double(
                                Point3DConverter.convertPoint(this.trajectories[i].getTrajectories()[k]),
                                Point3DConverter.convertPoint(this.trajectories[i].getTrajectories()[k + 1])));
                    }
                }

            }
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            if (REPORT)
                simulation.getReporter().report("Removed body from graphics");
            this.init();
        }
    }

    @Override
    public void init() {
        this.planetsPositions = new Point3D[simulation.getSystem().getCelestialBodies().size()];
        this.radius = new double[simulation.getSystem().getCelestialBodies().size()];
        this.trajectories = new Bag[this.planetsPositions.length];
        for (int i = 0; i < this.planetsPositions.length; i++) {
            if (DRAW_TRAJECTORIES)
                this.trajectories[i] = new Bag();
            this.planetsPositions[i] = simulation.getSystem().systemState().getPositions().get(i).fromVector();
            this.planetsPositions[i].scale(scale);
            radius[i] = (simulation.getSystem().getCelestialBodies().get(i).getRADIUS() / scale) * Point3DConverter.getScale() * radiusMag;
        }
    }

    /**
     * Get planet positions point 3 d [ ].
     *
     * @return the point 3 d [ ]
     */
    public Point3D[] getPlanetPositions() {
        return this.planetsPositions;
    }

    public void update() {
        super.update();
        try {
            this.updateBodies();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            if (simulation != null)
                try {
                    this.init();
                } catch (NullPointerException ignored) {
                }
        }
        super.resetMouse();
    }

    /**
     * Update bodies.
     */
    public void updateBodies() {

        //TODO : try translate planetPositions points by Vector.sub(oldPosition)
        double x = totalXDif / mouseSensitivity, y = totalYDif / mouseSensitivity, dx = deltaX / mouseSensitivity, dy = deltaY / mouseSensitivity;
        for (int i = 0; i < this.planetsPositions.length; i++) {
            this.planetsPositions[i] = simulation.getSystem().systemState().getPositions().get(i).fromVector();
            this.planetsPositions[i].scale(scale);
            radius[i] = (simulation.getSystem().getCelestialBodies().get(i).getRADIUS() / scale) * Point3DConverter.getScale() * radiusMag;
            Point3DConverter.rotateAxisY(this.planetsPositions[i], false, x);
            Point3DConverter.rotateAxisX(this.planetsPositions[i], false, y);

            if (DRAW_TRAJECTORIES) {
                if (!simulation.waiting())
                    this.trajectories[i].add(this.planetsPositions[i]);
                for (int k = 0; k < this.trajectories[i].getTrajectories().length; k++) {
                    if (this.trajectories[i].getTrajectories()[k] == null)
                        break;
                    //most pleasant "bug" of my life - change delta x and y to be xdiff and ydiff - rotate the scene
                    Point3DConverter.rotateAxisY(this.trajectories[i].getTrajectories()[k], false, dx);
                    Point3DConverter.rotateAxisX(this.trajectories[i].getTrajectories()[k], false, dy);
                }
            }
        }

    }


    /**
     * Planet shape ellipse 2 d . double.
     *
     * @param position the position
     * @param radius   the radius
     * @return the ellipse 2 d . double
     */
    Ellipse2D.Double planetShape(Point3D position, double radius) {
        Point p = Point3DConverter.convertPoint(position);
        return new Ellipse2D.Double(p.getX() - radius, p.getY() - radius, radius * 2, radius * 2);
    }

    @Override
    public String toString() {
        return "SimulationScene{" +
                "planetsPositions=" + Arrays.toString(planetsPositions) +
                '}';
    }


    /**
     * The type Bag.
     */
    class Bag {
        /**
         * The Trajectories.
         */
        final Point3D[] trajectories;
        /**
         * The Insert.
         */
        int insert;
        /**
         * The Loop.
         */
        boolean loop;

        /**
         * Instantiates a new Bag.
         */
        @Contract(pure = true)
        Bag() {
            this.trajectories = new Point3D[TRAJECTORY_LENGTH];
            this.insert = 0;
            this.loop = false;
        }

        /**
         * Add.
         *
         * @param p the p
         */
        void add(final Point3D p) {
            this.trajectories[this.insert] = p;
            this.insert++;
            if (this.insert == this.trajectories.length) {
                this.insert = 0;
            }
        }

        /**
         * Get trajectories point 3 d [ ].
         *
         * @return the point 3 d [ ]
         */
        Point3D[] getTrajectories() {
            return this.trajectories;
        }

    }
}
