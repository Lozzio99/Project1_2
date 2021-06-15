package phase3.Math.Solvers;

import phase3.Math.Functions.Function;

public class SimpsonsIntegration implements Integration {

    public double apply(Function<Double> fX, double h, double x0, double xf) {
        int N = (int) ((xf - x0) / h);
        double sum = (fX.apply(x0)+fX.apply(xf));
        for (int i = 1; i < N; i+=2) {
            sum += fX.apply(x0+h*i)*4.0;
        }
        for (int i = 2; i < N; i+=2) {
            sum += fX.apply(x0+h*i)*2.0;
        }
        return h*sum/3.0;
    }

}
