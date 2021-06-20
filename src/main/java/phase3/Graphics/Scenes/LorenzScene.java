package phase3.Graphics.Scenes;

import phase3.Graphics.MouseInput;
import phase3.Math.ADT.Vector3DConverter;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Simulation.SimulationInterface;
import phase3.System.State.StateInterface;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static phase3.Config.DRAW_TRAJECTORIES;
import static phase3.Graphics.GraphicsInterface.screen;
import static phase3.Math.ADT.Vector2DConverter.getScale;
import static phase3.Math.ADT.Vector3DConverter.rotateAxisX;
import static phase3.Math.ADT.Vector3DConverter.rotateAxisY;

public class LorenzScene extends Scene {
    private final static Color BG = new Color(0, 0, 0);
    static int i = 0;
    private double[] radius;
    private Vector3dInterface[] planetsPositions;
    private SimulationScene.Bag[] trajectories;

    public LorenzScene(SimulationInterface s) {
        super(s);
    }

    @Override
    public void init() {
        for (int i = 0; i < 30; i++)
            Vector3DConverter.zoomIn();

        this.radius = new double[simulation.getSystem().getState().get().length];
        this.planetsPositions = new Vector3dInterface[simulation.getSystem().getState().get().length];
        this.trajectories = new SimulationScene.Bag[this.planetsPositions.length];
        for (int i = 0; i < this.radius.length; i++) {
            if (DRAW_TRAJECTORIES) this.trajectories[i] = new SimulationScene.Bag();
            this.planetsPositions[i] = simulation.getSystem().getState().get()[i];
            this.radius[i] = (simulation.getSystem().getCelestialBodies().get(i).getRADIUS()) * getScale();
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        createImage(g);

        //if gif making ->
        /*
        BufferedImage bi = new BufferedImage(screen.width,screen.height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D save = bi.createGraphics();
        createImage(save);
        saveImage("src/main/resources/Gifs/lorenz"+(i++)+".png",bi);
        if (i == 200) System.exit(0);
         */
    }


    private void createImage(Graphics2D g) {
        g.setColor(BG);
        g.fill3DRect(0, 0, screen.width, screen.height, true);
        for (int i = 0; i < this.state.get().length; i++) {
            Ellipse2D.Double e = planetShape(this.planetsPositions[i], this.radius[i]);
            g.setColor(this.simulation.getSystem().getCelestialBodies().get(i).getColour());
            g.fill(e);
            drawTrajectories(g, i, this.trajectories);
        }
    }


    @Override
    public void addMouseControl(MouseInput mouse) {
        this.mouse = mouse;
    }

    public void update(StateInterface<Vector3dInterface> state) {
        super.update(state);
        this.updateBodies();
        super.resetMouse();
    }

    public void updateBodies() {
        double x = totalXDif2 / mouseSensitivity, y = totalYDif2 / mouseSensitivity, dx = deltaX2 / mouseSensitivity, dy = deltaY2 / mouseSensitivity;
        for (int i = 0; i < this.planetsPositions.length; i++) {
            this.planetsPositions[i] = state.get()[i].clone();
            radius[i] = (simulation.getSystem().getCelestialBodies().get(i).getRADIUS()) * getScale();
            rotateAxisY(this.planetsPositions[i], false, x);
            rotateAxisX(this.planetsPositions[i], false, y);
            if (DRAW_TRAJECTORIES)
                updateTrajectories(i, dx, dy, simulation.isRunning(), this.trajectories, this.planetsPositions);

        }

    }
}
