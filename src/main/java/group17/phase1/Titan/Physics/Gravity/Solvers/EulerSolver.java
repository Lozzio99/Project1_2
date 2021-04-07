package group17.phase1.Titan.Physics.Gravity.Solvers;

public class EulerSolver
{

    double step_size;
    Function f;

    public EulerSolver(double step_size, Function f) {
        this.step_size = step_size;
        this.f = f;
    }

    public double solver()
    {
        for (double i = 0; i <= 2+this.step_size; i = i + this.step_size){
            f.setX(i);
            f.setY(eulers_method(f));
        }
        return f.getY();
    }

    public double eulers_method(Function f)
    {
        return f.getY() + this.step_size*f.xy(f.getY(),f.getX());
    }

}
 /* y1 = y0 + f'(x0,y0);
    y(0) = 2
    dy/dx = x*y
    dy/y = x dx
    lny = x^2/2 + C
    y = C1*e^(x^2/2);
    2 = C1 *e^0
    2 = C1*1
    C1 = 2;
    {  y = 2*e^(x^2/2);  }


    x = 2;


    y(2) = 2*e^2 = 14.778...

    z = x*y;

 */