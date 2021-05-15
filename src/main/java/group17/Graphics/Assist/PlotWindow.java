package group17.Graphics.Assist;

import group17.Interfaces.Function;
import group17.Math.Utils.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;

import static java.lang.StrictMath.sin;

public class PlotWindow extends JPanel {

    GraphPane leftGraph, rightGraph;


    public PlotWindow() {
        this.setLayout(new GridLayout(1, 2, 100, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));
    }


    public PlotWindow init() {
        this.add(this.leftGraph = new GraphPane());
        this.add(this.rightGraph = new GraphPane());
        this.repaint();
        return this;
    }

    public PlotWindow test() {
        Function<Double> f = x -> 80 * sin(x / 17);
        double[] xs = new double[1000], ys = new double[1000];
        int x_ = GraphPane.graphSize.width / 2;
        double k = -x_;
        for (int i = 0; i < xs.length; i++) {
            xs[i] = k;
            ys[i] = f.apply(k);
            k += 0.5;
        }
        this.evaluate(xs, ys, 0);
        return this;
    }

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


    public static class GraphPane extends JPanel {
        public static Dimension graphSize = new Dimension(450, 450);
        public static Point ORIGIN;
        private final boolean plot2 = false;
        private final boolean drawAxis = true;
        private Point[] p1, p2;
        private Line2D.Double xAxis, yAxis;
        private boolean calculated = false;

        public GraphPane() {
            this.setSize(graphSize);
            Point.setScreen(graphSize);
            Point.setOrigin(new Point());
            if (drawAxis) this.makeAxis();
        }

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
