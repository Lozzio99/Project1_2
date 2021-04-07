package group17.phase1.Titan.Physics.Gravity.Solvers;

public class Driver {
    public static void main(String[] args){
    Function f = new Function(0,2);

    //EulerSolver y_solve = new EulerSolver(0.001,f);
    //System.out.println(y_solve.solver());

    RungeKuttaSolver r_solve = new RungeKuttaSolver(0.00001,f);
    System.out.println(r_solve.solver());

    }
}
