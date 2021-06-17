package phase3.Math.Solvers;

import phase3.Math.Functions.Function;

public class TrapezoidIntegration implements Integration {

    public double apply(Function<Double, Double> fX, double h, double x0, double xf) {
        int N = (int) ((xf - x0) / h);
        double sum = (fX.apply(x0) + fX.apply(xf)) * 0.5;
        for (int i = 1; i < N; i++) {
            sum += fX.apply(x0 + h * i);
        }
        return h * sum;
    }

}
