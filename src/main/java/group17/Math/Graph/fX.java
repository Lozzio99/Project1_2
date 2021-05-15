package group17.Math.Graph;
@FunctionalInterface
public interface fX {
    double f_x(double x);

    static void main(String[] args) {
        fX f = x -> x * 2;
        fX g = x -> x + 10;
        //f(g(x))  =  f(x+10) =  (x+10)*2
        System.out.println(f.fg_x(g, 10));
    }

    default double sum(double... xs) {
        double sum = 0;
        for (double x : xs) sum += f_x(x);
        return sum;
    }

    default double product(double... xs) {
        double prod = 1;
        for (double x : xs) prod *= x;
        return prod;
    }

    default double fg_x(fX g, double val) {
        return f_x(g.f_x(val));
    }
}
