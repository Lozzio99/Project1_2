package group17.phase1.Titan.Physics.Gravity.Solvers;

public class Function {
    double x;
    double y;
    public Function(double x, double y)
    {
        this.x=x;
        this.y=y;
    }

    public double xy(double x, double y)
    { // the function dy/dx = x*y; you can change this for other functions
        return x*y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
