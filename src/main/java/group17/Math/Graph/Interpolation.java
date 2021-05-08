package group17.Math.Graph;

public abstract class Interpolation {

    public fX f;
    public double[] xs;
    public double[] ys;

    public Interpolation(fX f, double... x) {
        this.f = f;
        xs = x;
        ys = fill(xs);
    }

    public static double[] linX(double a, double b, int n) {
        double[] xs = new double[n];
        double h = (b - a) / n;
        for (int i = 0; i < n; i++) {
            xs[i] = a += h;
        }
        return xs;
    }

    public static double[] linX(double[] sortedX, int n) {
        return linX(sortedX[0], sortedX[sortedX.length - 1], n);
    }

    public double[] fill(double[] xs) {
        double[] y = new double[xs.length];
        for (int i = 0; i < xs.length; i++) {
            y[i] = this.f.f_x(xs[i]);
        }
        return y;
    }

    public abstract double polynomial(double x);

    public double[] getXs() {
        return xs;
    }

    public double[] getYs() {
        return ys;
    }
}
