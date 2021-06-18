package phase3.Math.Integration;

import phase3.Math.Functions.Function;

public interface Integration {

    double integrate(Function<Double, Double> fX, double h, double a, double b);

}
