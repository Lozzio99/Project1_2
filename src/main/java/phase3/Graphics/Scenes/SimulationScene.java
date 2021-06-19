package phase3.Graphics.Scenes;

import phase3.Graphics.MouseInput;
import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.System.State.StateInterface;
import phase3.System.State.SystemState;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import static phase3.Config.*;
import static phase3.Main.simulation;
import static phase3.Math.ADT.Vector3DConverter.*;

/**
 * The type Simulation scene.
 */
public class SimulationScene extends Scene {


    private static final BufferedImage[] planets = new BufferedImage[11];
    /**
     * The Planets positions.
     */
    Vector3dInterface[] planetsPositions;
    /**
     * The Radius.
     */
    double[] radius;
    /**
     * The Trajectories.
     */
    Bag[] trajectories;


    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);
        super.paintImage(g, flightImage);
        try {
            for (int i = 0; i < this.planetsPositions.length; i++) {
                final Point2D.Double p = convertPoint(this.planetsPositions[i]);
                if (NAMES) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g.drawString(simulation.getSystem().getCelestialBodies().get(i).toString(), (int) p.getX(), (int) p.getY());
                }
                g.setColor(simulation.getSystem().getCelestialBodies().get(i).getColour());
                if (DRAW_TRAJECTORIES) {
                    for (int k = this.trajectories[i].insert; k < this.trajectories[i].getTrajectories().length - 1; k++) {
                        if (this.trajectories[i].getTrajectories()[k + 1] == null)
                            break;
                        g.draw(new Line2D.Double(
                                convertPoint(this.trajectories[i].getTrajectories()[k]),
                                convertPoint(this.trajectories[i].getTrajectories()[k + 1])));
                    }
                    for (int k = 0; k < this.trajectories[i].insert - 1; k++) {
                        if (this.trajectories[i].getTrajectories()[k + 1] == null)
                            break;
                        g.draw(new Line2D.Double(
                                convertPoint(this.trajectories[i].getTrajectories()[k]),
                                convertPoint(this.trajectories[i].getTrajectories()[k + 1])));
                    }
                }
                g.fill(planetShape(this.planetsPositions[i], this.radius[i]));

            }
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        this.planetsPositions = new Vector3dInterface[simulation.getSystem().getState().get().length / 2];
        this.state = new SystemState<>(new Vector3D[this.planetsPositions.length]);
        this.radius = new double[this.planetsPositions.length];
        this.trajectories = new Bag[this.planetsPositions.length];
        for (int i = 0; i < this.planetsPositions.length; i++) {
            if (DRAW_TRAJECTORIES) this.trajectories[i] = new Bag();
            this.planetsPositions[i] = simulation.getSystem().getState().get()[i].div(scale);
            radius[i] = (simulation.getSystem().getCelestialBodies().get(i).getRADIUS() / scale) * getScale() * radiusMag;
        }
    }


    @Override
    public void addMouseControl(MouseInput mouse) {
        this.mouse = mouse;
    }

    /**
     * Get planet positions point 3 d [ ].
     *
     * @return the point 3 d [ ]
     */
    public Vector3dInterface[] getPlanetPositions() {
        return this.planetsPositions;
    }

    public void update(StateInterface<Vector3dInterface> state) {
        super.update(state);
        this.updateBodies();
        super.resetMouse();
    }

    /**
     * Update bodies.
     */
    public void updateBodies() {

        double x = totalXDif2 / mouseSensitivity, y = totalYDif2 / mouseSensitivity, dx = deltaX2 / mouseSensitivity, dy = deltaY2 / mouseSensitivity;
        for (int i = 0; i < this.planetsPositions.length; i++) {
            this.planetsPositions[i] = state.get()[i].div(scale);
            radius[i] = (simulation.getSystem().getCelestialBodies().get(i).getRADIUS() / scale) * getScale() * radiusMag;
            rotateAxisY(this.planetsPositions[i], false, x);
            rotateAxisX(this.planetsPositions[i], false, y);

            if (DRAW_TRAJECTORIES) {
                if (simulation.isRunning())
                    this.trajectories[i].add(this.planetsPositions[i]);
                for (int k = 0; k < this.trajectories[i].getTrajectories().length; k++) {
                    if (this.trajectories[i].getTrajectories()[k] == null) break;
                    //most pleasant "bug" of my life - change delta x and y to be xdiff and ydiff - rotate the scene
                    rotateAxisY(this.trajectories[i].getTrajectories()[k], false, dx);
                    rotateAxisX(this.trajectories[i].getTrajectories()[k], false, dy);
                }
            }
        }

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
    static class Bag {
        /**
         * The Trajectories.
         */
        final Vector3dInterface[] trajectories;
        /**
         * The Insert.
         */
        int insert;

        /**
         * Instantiates a new Bag.
         */
        Bag() {
            this.trajectories = new Vector3dInterface[TRAJECTORY_LENGTH];
            this.insert = 0;
        }

        /**
         * Add.
         *
         * @param p the p
         */
        void add(final Vector3dInterface p) {
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
        Vector3dInterface[] getTrajectories() {
            return this.trajectories;
        }

    }
}
