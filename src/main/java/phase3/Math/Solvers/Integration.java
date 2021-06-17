package phase3.Math.Solvers;

import phase3.Math.Functions.Function;

public interface Integration {

    double apply(Function<Double, Double> fX, double h, double x0, double xf);

}
