package group17.phase1.Titan.Physics.Gravity.Solvers;

public class RungeKuttaSolver {
    double step_size;
    Function f;
    public RungeKuttaSolver(double step_size, Function f){
        this.step_size = step_size;
        this.f = f;
    }

    public double calculateKs(){
        double[] ks = new double[4];
        ks[0] = step_size*f.xy(f.getX(),f.getY());
        ks[1] = step_size*f.xy(f.getX()+step_size*0.5,f.getY()+ks[0]*0.5);
        ks[2] = step_size*f.xy(f.getX()+step_size*0.5,f.getY()+ks[1]*0.5);
        ks[3] = step_size*f.xy(f.getX()+step_size,f.getY()+ks[2]);

        return f.getY()+  1.0/6.0* (ks[0]+2*ks[1]+2*ks[2]+ks[3]);
    }
    public double solver()
    {
        for (double i = 0; i <= 2 ; i = i + step_size){
            f.setX(i);
            //System.out.println(f.getX());
            f.setY(calculateKs());
        }
        return f.getY();
    }


}