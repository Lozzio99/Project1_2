package group17.Graphics.Assist;

import javax.swing.*;
import java.awt.*;

/**
 * * * FUTURE feature * * *
 * <p>
 * <p>
 * class to display valuable information about the rocket,
 * such as fuel left, heading, acceleration...
 */
@Deprecated(forRemoval = false)
public class RocketWindow extends JPanel {
    private final Color[] fuelState = new Color[]{
            new Color(117, 255, 97),
            new Color(255, 230, 0),
            new Color(255, 140, 0),
            new Color(255, 0, 0)
    };
    private int baseFuelConsumed = 0, baseFuel = 0, lightFuelConsumed = 0, lightFuel = 0;

    /**
     * Instantiates a new Rocket window.
     */
    public RocketWindow() {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Rocket Info"));

        /*
        JButton j = new JButton("update base");
        j.setSize(200, 50);
        this.add(j);
        RocketWindow p = this;
        j.addActionListener(e -> {
            p.updateBaseFuel();
            p.update(p.getGraphics());
        });

        JButton j2 = new JButton("update light");
        j2.setSize(200, 50);
        this.add(j2);

        j2.addActionListener(e -> {
            p.updateLightFuel();
            p.update(p.getGraphics());
        });
        */
    }

    /**
     * Update base fuel.
     */
    public void updateBaseFuel() {
        if (baseFuel == 4)
            return;
        baseFuelConsumed += 100; //4 steps to finish it
        baseFuel++;
    }

    /**
     * Update light fuel.
     */
    public void updateLightFuel() {
        if (lightFuel == 4)
            return;
        lightFuelConsumed += 100; //4 steps to finish it
        lightFuel++;
    }

    /**
     * Reset.
     */
    public void reset() {
        baseFuel = lightFuelConsumed = baseFuelConsumed = lightFuel = 0;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;
        int[] baseX = new int[]{400, 800, 800, 400}, baseY = {350, 350, 200, 200};
        int[] topX = new int[]{150, 200, 400, 400, 200}, topY = {275, 350, 350, 200, 200};
        int[] rThrustX = new int[]{500, 900, 950, 550}, rThrustY = {200, 200, 100, 100};
        int[] lThrustX = new int[]{550, 950, 900, 500}, lThrustY = {450, 450, 350, 350};


        if (baseFuel < 4) {
            g.setColor(fuelState[baseFuel]);
            fillFuel(g, baseX, baseY, 800, baseFuelConsumed);
        }

        if (lightFuel < 4) {
            g.setColor(fuelState[lightFuel]);
            fillFuel(g, lThrustX, lThrustY, 950, lightFuelConsumed);
            fillFuel(g, rThrustX, rThrustY, 950, lightFuelConsumed);
        }

        g.setColor(new Color(25, 143, 255));
        fillShape(g, topX, topY);

        g.setColor(new Color(0, 0, 0));
        drawShape(g, baseX, baseY);
        drawShape(g, topX, topY);
        drawShape(g, rThrustX, rThrustY);
        drawShape(g, lThrustX, lThrustY);
    }

    private void drawShape(Graphics2D g, int[] xs, int[] ys) {
        Polygon p = new Polygon();
        for (int i = 0; i < xs.length; i++) {
            p.addPoint(xs[i], ys[i]);
        }
        g.setStroke(new BasicStroke(4.f));
        g.draw(p);
    }

    private void fillFuel(Graphics2D g, int[] xs, int[] ys, int max, int fuelConsumed) {
        Polygon p = new Polygon();
        for (int i = 0; i < xs.length; i++) {
            if (xs[i] + fuelConsumed > max)
                p.addPoint(xs[i], ys[i]);
            else
                p.addPoint(xs[i] + fuelConsumed, ys[i]);
        }
        g.fill(p);
    }

    /**
     * Fill shape.
     *
     * @param g  the g
     * @param xs the xs
     * @param ys the ys
     */
    public void fillShape(Graphics2D g, int[] xs, int[] ys) {
        Polygon p = new Polygon();
        for (int i = 0; i < xs.length; i++) {
            p.addPoint(xs[i], ys[i]);
        }
        g.fill(p);
    }


}
