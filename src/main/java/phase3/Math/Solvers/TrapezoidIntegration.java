package phase3.Math.Solvers;

import phase3.Math.Functions.Function;

public class TrapezoidIntegration {

    public double apply(Function<Double> fX, double h, double x0, double xf) {
        assert xf>x0;
        double[] T = new double[(int)((xf-x0)/h)];
        for (int i = 0; i < T.length; i++) {
            T[i] = x0+h*i;
        }
        double sum = 0.0;
        for (int i = 1; i < T.length-1; i++) {
            sum =+ fX.apply(T[i]);
        }
        return h*((fX.apply(x0)+fX.apply(xf))*0.5+sum);
    }

}
