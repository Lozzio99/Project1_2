package group17.Math.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferStrategy;
import java.util.Arrays;

import static java.lang.Math.sin;

public class Plot extends Canvas {
    public static final int DIVIDED_DIFFERENCES = 1, LINEAR_LEAST_SQUARES = 2, QUADRATIC_SQUARES = 3, LEGENDRE = 4, LAGRANGE = 5, CHEBYSHEV = 6;
    final static Dimension screen = new Dimension(500, 500);
    private static final int sz = 400;
    static Point ORIGIN;
    private static int FIT = 1;
    private final JFrame frame;
    private final boolean drawAxis = true;
    private fX f;
    private WindowEvent listen;
    private Point[] p1, p2;
    private Line2D.Double x, y;
    private boolean calculated = false;
    private boolean plot2 = false;

    public Plot() {
        this.frame = new JFrame();
        this.frame.setSize(screen);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                listen = new WindowEvent(frame, 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                System.exit(0);
            }
        });
        ImageIcon img = new ImageIcon(this.getClass().getClassLoader().getResource("icons/death.png").getFile());
        this.frame.setIconImage(img.getImage());
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.add(this);
        this.frame.setVisible(true);
        Point.setScreen(screen);
        ORIGIN = new Point();
        if (drawAxis)
            this.axis();
        this.start();
    }

    public static void main(String[] args) {
        Plot p = new Plot();
        p.scale(-2);
        //p.plot(Math::exp);
        p.plot((x) -> 80 * sin(x * 50));
        //p.evaluate(-200,0,0,100,200,0);
    }

    /**
     * sorts the arrays by reference
     *
     * @param xs the arrays which will give the sorting order
     * @param ys the xs index - related values
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

    public static void sort(double[] xs) {
        int n = xs.length;
        for (int j = 1; j < n; j++) {
            double key = xs[j];
            int i = j - 1;
            while ((i > -1) && (xs[i] > key)) {
                xs[i + 1] = xs[i];
                i--;
            }
            xs[i + 1] = key;
        }
    }

    public Plot setFit(int fit) {
        FIT = fit;
        return this;
    }

    public Plot plot(fX f) {
        this.f = f;
        this.plot();
        return this;
    }

    /**
     * Requires sorted pairs of drawX and drawY 's
     */
    public Plot plot(double... xy) {
        calculated = false;
        if (xy.length % 2 != 0)
            throw new IllegalArgumentException("Please fill drawX,drawY - drawX,drawY - drawX,drawY...");
        this.p1 = new Point[xy.length / 2];
        this.p2 = new Point[xy.length - this.p1.length];
        int k = 0;
        for (int i = 0; i < this.p1.length; i++) {
            this.p1[i] = new Point(xy[k], xy[k + 1]);
            k += 2;
        }
        for (int i = 0; k < this.p2.length; i++) {
            this.p2[i] = new Point(xy[k], xy[k + 1]);
            k += 2;
        }
        calculated = true;
        return this;
    }

    public Plot plot(double[] xs, double[] ys) {
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

    public Plot plotF(fX f, double[] xs) {
        double[] x = Interpolation.linX(-100, 100, 400);
        double[] y = new double[400];
        for (int i = 0; i < 400; i++)
            y[i] = f.f_x(x[i]);
        return this.plot(x, y);
    }

    public Plot plotF2(fX f, double[] xs) {
        double[] x = Interpolation.linX(-100, 100, 400);
        double[] y = new double[400];
        for (int i = 0; i < 400; i++)
            y[i] = f.f_x(x[i]);
        return this.plot2(x, y);
    }

    public Plot plot2(double[] xs, double[] ys) {
        calculated = false;
        plot2 = true;
        double[] x, y;
        x = Arrays.copyOf(xs, xs.length);
        y = Arrays.copyOf(ys, ys.length);
        sortingForDrawing(x, y);
        this.p2 = new Point[xs.length];
        for (int i = 0; i < this.p2.length; i++) {
            this.p2[i] = new Point(x[i], y[i]);
        }
        calculated = true;
        return this;
    }

    private void axis() {
        Point px = new Point(-400, 0), py = new Point(0, -400), px1 = new Point(400, 0), py1 = new Point(0, 400);
        this.x = new Line2D.Double(px.getX(), px.getY(), px1.getX(), px1.getY());
        this.y = new Line2D.Double(py.getX(), py.getY(), py1.getX(), py1.getY());
    }

    private void plot() {
        plot2 = calculated = false;
        int x_ = screen.width * 2;
        this.p1 = new Point[x_];
        this.p2 = new Point[x_];
        double k = -x_ / 2.;
        for (int i = 0; k < 0 /* watch out this k */; i++) {
            p1[i] = new Point(k, f.f_x(k));
            k += 0.5;
        }
        for (int i = 0; i < x_; i++) {
            p2[i] = new Point(k, f.f_x(k));
            k += 0.5;
        }
        calculated = true;
    }

    private void start() {
        Timer t = new Timer(100, (e) -> render());
        t.start();
    }

    public void translate(double x, double y) {
        ORIGIN.xOff += x;
        ORIGIN.yOff += y;
        if (drawAxis)
            this.axis();
    }

    public void scale(double scale) {
        Point.scale += scale;
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bs.getDrawGraphics();
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(76, 76, 76, 190));
        g.fill3DRect(0, 0, screen.width, screen.height, true);
        draw(g);
        g.dispose();
        bs.show();
    }

    private void draw(Graphics2D g) {
        if (drawAxis) {
            g.setColor(new Color(255, 0, 0, 156));
            g.draw(this.x);
            g.draw(this.y);
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


            if (p2 != null) {
                if (plot2) {
                    g.setColor(new Color(85, 255, 79, 91));
                    g.drawString("p2", 10, 25);
                    g.setStroke(new BasicStroke(1.0f));
                } else {

                    //dangerous too
                    g.draw(new Line2D.Double(p1[p1.length - 1].getX(), p1[p1.length - 1].getY(), p2[0].getX(), p2[0].getY()));
                }
                for (int i = 0; i < this.p2.length - 1; i++) {
                    if (p2[i + 1] == null || p2[i] == null)
                        continue;
                    g.draw(new Line2D.Double(p2[i + 1].getX(), p2[i + 1].getY(), p2[i].getX(), p2[i].getY()));
                }
            }
        }

    }

    public Plot fit(double[] xs, double[] ys) {
        switch (FIT) {
            case DIVIDED_DIFFERENCES -> {
                DividedDifferences d = new DividedDifferences(xs, ys);
                double[] x1 = Arrays.copyOf(xs, xs.length);
                sort(x1);
                double[] x2 = Interpolation.linX(x1, sz), y2;
                y2 = d.fx(x2);
                return plot(x2, y2);
            }

        }
        return plot(xs, ys);
    }
}
