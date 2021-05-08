package group17.Math.Graph;

import java.util.Arrays;

import static java.lang.Double.NaN;

public class DividedDifferences {
    double[] xs;
    double[] ys;
    double[][] fx;
    //P(X)  var
    double px;

    public DividedDifferences(double[] xs, double[] ys) {
        this.xs = xs;
        this.ys = ys;
    }

    public DividedDifferences(double... x_y) {
        if (x_y.length % 2 != 0)
            throw new RuntimeException("fill in : xs,ys - xs,ys - ...");
        this.xs = new double[x_y.length / 2];
        this.ys = new double[x_y.length / 2];
        int k = 0;
        for (int i = 0; i < x_y.length; i += 2) {
            this.xs[k] = x_y[i];
            this.ys[k] = x_y[i + 1];
            k++;
        }
    }

    public static void main(String[] args) {
        //new DividedDifferences(1,5,3,1,-2,-4,4,9.5).printTable();

        DividedDifferences d = new DividedDifferences(1, 0.3, -4, 1.3, 0, -2.3);
        d.printTable();
        double px = d.p(0, 0);
        System.out.println(px);
        System.out.println(Arrays.toString(d.fx()));
        System.out.println(Arrays.toString(d.fx(d.xs)));

        System.out.println(Arrays.toString(d.fx(new double[]{-4, -3, -2, -1, 0, 1})));
        // new DividedDifferences(2.5,0.68,2.0,0.66,3.0,0.9,1.5,0.79,3.5,1.31,1,1.02,4.0,1.77,0.5,1.3,4.5,1.87,0,1.5,5,1.33).printTable();

    }

    public double[][] getTable() {
        this.fx = new double[this.xs.length][this.xs.length];
        for (double[] c : fx)
            Arrays.fill(c, NaN);
        this.fx[0] = this.ys;
        for (int i = 1; i < this.xs.length; i++) {
            int left = i - 1;
            for (int k = 0; k < this.xs.length - i; k++) {
                this.fx[i][k] = (this.fx[left][k + 1] - this.fx[left][k]) / (this.xs[i + k] - this.xs[k]);
            }
        }
        return this.fx;
    }

    /**
     * @param xk the index of the xs (in the given xs)
     * @param i  the starting point
     * @return recursively the p(xs)
     */
    private double p(int xk, int i) {
        if (i == 0)
            px = 0;
        px = this.fx[i][0];
        if (i <= this.fx.length - 2)
            return px += (this.xs[xk] - this.xs[i]) * p(xk, i + 1);
        return px;
    }

    /**
     * evaluate at a given xs value
     *
     * @param x_ the value xs to evaluate
     * @param i  the index of the starting point
     * @return recursively the p(xs)
     */
    private double p(double x_, int i) {
        if (i == 0)
            px = 0;
        px = this.fx[i][0];
        if (i <= this.fx.length - 2)
            return px += (x_ - this.xs[i]) * p(x_, i + 1);
        return px;
    }

    /**
     * @param xs the values on which to test the polynomial
     * @param xk the index of the xs to evaluate (from given xs)
     * @return the polynomial evaluated at xs
     */
    private double p(double[] xs, int xk, int start) {
        if (start == 0)
            px = 0;
        px = this.fx[start][0];
        if (start <= this.fx.length - 2)
            return px += (xs[xk] - xs[start]) * p(xk, start + 1);
        return px;
    }

    /**
     * @param xs the values on which to test the polynomial
     * @param x_ the value of the xs to evaluate (from non - given xs too)
     * @return the polynomial evaluated at xs
     */
    private double p(double[] xs, double x_, int start) {
        if (start == 0)
            px = 0;
        px = this.fx[start][0];
        if (start <= this.fx.length - 2)
            return px += (x_ - xs[start]) * p(x_, start + 1);
        return px;
    }

    /**
     * get the ys values from evaluated xs
     *
     * @param xs the values at which to evaluate the p
     * @return the ys filled with approximated p(x_i)
     */
    public double[] fx(double[] xs) {
        if (this.fx == null)
            this.getTable();
        double[] fx = new double[xs.length];
        for (int i = 0; i < xs.length; i++) {
            fx[i] = p(xs[i], 0);
        }
        return fx;
    }

    /**
     * get the ys values evaluated from given xs
     *
     * @return the ys filled with approximated p(x_i)
     */
    public double[] fx() {
        if (this.fx == null)
            this.getTable();
        double[] fx = new double[xs.length];
        for (int i = 0; i < xs.length; i++) {
            fx[i] = p(xs, i, 0);
        }
        return fx;
    }

    public void printTable() {
        this.fx = this.getTable();
        for (int i = 0; i < this.fx[0].length; i++) {
            System.out.print(this.xs[i] + " | ");
            for (int k = 0; k < this.fx.length; k++) {
                if (this.fx[k][i] >= 0)
                    System.out.print(" ");
                System.out.print((float) this.fx[k][i] + " | ");
            }
            System.out.println();
        }
    }

}
