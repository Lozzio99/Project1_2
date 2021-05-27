package group17.Graphics.Assist;

import group17.Math.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;

/**
 * * * FUTURE feature * * *
 * <p>
 * <p>
 * class to plot a valued function on two different graphs
 */

//@Deprecated(forRemoval = false)
public class PlotWindow extends JPanel {

    /**
     * The Left graph.
     */
    GraphPane leftGraph, /**
     * The Right graph.
     */
    rightGraph;


    /**
     * Instantiates a new Plot window.
     */
    public PlotWindow() {
        this.setLayout(new GridLayout(1, 2, 100, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));
    }


    /**
     * Init plot window.
     *
     * @return the plot window
     */
    public PlotWindow init() {
        this.add(this.leftGraph = new GraphPane());
        this.add(this.rightGraph = new GraphPane());
        this.repaint();
        return this;
    }


    /**
     * Evaluate.
     *
     * @param xs          the xs
     * @param ys          the ys
     * @param left0right1 the left 0 right 1
     */
    public void evaluate(double[] xs, double[] ys, int left0right1) {
        switch (left0right1) {
            case 0 -> {
                this.leftGraph.plot(xs, ys);
                this.leftGraph.repaint();
            }
            case 1 -> {
                this.rightGraph.plot(xs, ys);
                this.rightGraph.repaint();
            }
        }
    }


    /**
     * The type Graph pane.
     */
    public static class GraphPane extends JPanel {
        /**
         * The constant graphSize.
         */
        public static Dimension graphSize = new Dimension(450, 450);
        /**
         * The constant ORIGIN.
         */
        public static Point ORIGIN;
        private final boolean plot2 = false;
        private final boolean drawAxis = true;
        private Point[] p1, p2;
        private Line2D.Double xAxis, yAxis;
        private boolean calculated = false;

        /**
         * Instantiates a new Graph pane.
         */
        public GraphPane() {
            this.setSize(graphSize);
            Point.setScreen(graphSize);
            Point.setOrigin(new Point());
            if (drawAxis) this.makeAxis();
        }

        /**
         * Sorting for drawing.
         *
         * @param xs the xs
         * @param ys the ys
         */
        public static void sortingForDrawing(double[] xs, double[] ys) {
            int n = xs.length;
            for (int j = 1; j < n; j++) {
                double key = xs[j], y_key = ys[j];
                int i = j - 1;
                while ((i > -1) && (xs[i] > key)) {
                    xs[i + 1] = xs[i];
                    ys[i + 1] = ys[i];
                    i--;
                }
                xs[i + 1] = key;
                ys[i + 1] = y_key;
            }
        }

        private void makeAxis() {
            Point px = new Point(-400, 0), py = new Point(0, -400), px1 = new Point(400, 0), py1 = new Point(0, 400);
            this.xAxis = new Line2D.Double(px.getX(), px.getY(), px1.getX(), px1.getY());
            this.yAxis = new Line2D.Double(py.getX(), py.getY(), py1.getX(), py1.getY());
        }

        /**
         * Plot graph pane.
         *
         * @param xs the xs
         * @param ys the ys
         * @return the graph pane
         */
        public GraphPane plot(double[] xs, double[] ys) {
            calculated = false;
            if (xs.length != ys.length)
                throw new IllegalArgumentException("drawX and drawY must have the same size");
            if (xs.length > 1000)
                throw new UnsupportedOperationException("need to implement multiple points arrays");
            double[] x, y;
            x = Arrays.copyOf(xs, xs.length);
            y = Arrays.copyOf(ys, ys.length);
            sortingForDrawing(x, y);
            this.p1 = new Point[xs.length];
            for (int i = 0; i < this.p1.length; i++) {
                this.p1[i] = new Point(x[i], y[i]);
            }
            calculated = true;
            return this;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics;
            g.setClip(0, 0, graphSize.width, graphSize.height);
            g.setColor(new Color(233, 246, 243));
            g.fill3DRect(0, 0, graphSize.width, graphSize.height, true);
            if (drawAxis) {
                g.setColor(new Color(255, 0, 0, 156));
                g.draw(this.xAxis);
                g.draw(this.yAxis);
            }
            if (calculated) {
                g.setStroke(new BasicStroke(0.3f));
                g.setColor(new Color(0, 102, 255, 119));
                g.drawString("p1", 10, 13);
                for (int i = 0; i < this.p1.length - 1; i++) {
                    if (p1[i] == null || p1[i + 1] == null)
                        continue;
                    g.draw(new Line2D.Double(p1[i + 1].getX(), p1[i + 1].getY(), p1[i].getX(), p1[i].getY()));
                }
            }
            g.setClip(0, 0, getWidth(), getHeight());
        }
    }

}
