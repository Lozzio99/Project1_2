

package Solvers;



import Module.Math.ADT.Vector3D;
import Module.Math.ADT.Vector3dInterface;
import Module.Math.Functions.ODEFunctionInterface;
import Module.Math.Solvers.*;



import Module.System.State.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.sin;

/**
 * This Test uses the damped driven pendulum equation of motion, a second order ODE
 *¨x + delta*˙x + ro*sin(x)
 *
 * MATLAB test code (regarded as exactValue)
 * x0 = pi;
 * v0 = 1;
 * g = @(t,x,v) -v - sin(x);
 * f = @(t,y) [y(2);g(t,y(1),y(2))];
 * [ts,ys] = ode45(f,[0,0.2],y0);
*/
 class GeneralSystemTest {
 static ODESolverInterface<Vector3dInterface> solver;

 static StateInterface<Vector3dInterface> y;
 static double t, tf, stepSize;
 private final boolean debug = false;
 double exact1stepPos,exact1stepVel,exactFinalPos,exactFinalVel;

 static ODEFunctionInterface<Vector3dInterface> dydx = (t, y) -> {
 Vector3dInterface rateOfChange =  y.getRateOfChange().get();
 Vector3dInterface position =  y.get();



 double acceleration = -rateOfChange.get(0)-sin(position.get(0));

 return new RateOfChange<>(new Vector3D(acceleration,0,0));
 };

 static ODEFunctionInterface<Double> dy = (t,y)->{
 double x = y.get();
 double dx = y.getRateOfChange().get();
 return new RateOfChange<>(-1*dx - sin(x));
 };


 @BeforeEach
 void setup() {
 exact1stepPos = 3.146580195184224;
 exact1stepVel = 0.995024937596275;

 exactFinalPos = 3.324069835410512;
 exactFinalVel =  0.836261857680801;

 stepSize =  0.005000000000000;
 t = 0;
 tf = 0.2;
 double x1 = Math.PI;
 double x2 = 1;
 y = new SystemState(new Vector3D(x1,0,0));
 RateInterface<Vector3dInterface> rate = new RateOfChange(new Vector3D(x2,0,0));
 y.getRateOfChange().set(rate.get());

 }

 @Test
 @DisplayName("midPointSolve")
 void midPointSolve(){

 y =  solve(new MidPointSolver<>(dydx));


 printResults("MIDPOINT",exactFinalPos,exactFinalVel,y);
 assertTrue(() -> Math.abs(exactFinalPos - y.get().get(0))<1e-2);
 assertTrue(() -> Math.abs(exactFinalVel - y.getRateOfChange().get().get(0))<1e-2);

 }


 @Test
 @DisplayName("rkSolve")
 void rkSolve(){
 solve(new RungeKuttaSolver<>(dydx));

 printResults("RK",exactFinalPos,exactFinalVel,y);

 assertTrue(() -> Math.abs(exactFinalPos - y.get().get(0))<1e-2);
 assertTrue(() -> Math.abs(exactFinalVel - y.getRateOfChange().get().get(0))<3e-2);
 }
 @Test
 @DisplayName("eulerSolve")
 void eulerSolve(){
 solve(new EulerSolver<>(dydx));

 printResults("EULER",exactFinalPos,exactFinalVel,y);

 assertTrue(() -> Math.abs(exactFinalPos - y.get().get(0))<1e-2);
 assertTrue(() -> Math.abs(exactFinalVel - y.getRateOfChange().get().get(0))<1e-2);
 }
 @Test
 @DisplayName("StandardVerletSolve")
 void verletStdSolve(){
  solve(new StandardVerletSolver<>(dydx));

  printResults("StandartVerlet",exactFinalPos,exactFinalVel,y);

  assertTrue(() -> Math.abs(exactFinalPos - y.get().get(0))<1e-2);
  assertTrue(() -> Math.abs(exactFinalVel - y.getRateOfChange().get().get(0))<1e-2);
 }
 @Test
 @DisplayName("VerletVelocitySolve")
 void verletVelSolve(){
  solve(new VerletVelocitySolver<>(dydx));

  printResults("VerletVelocity",exactFinalPos,exactFinalVel,y);

 // assertTrue(() -> Math.abs(exactFinalPos - y.get().get(0))<1e-2);
 // assertTrue(() -> Math.abs(exactFinalVel - y.getRateOfChange().get().get(0))<3e-2);
 }

 @Test
 @DisplayName("midPointStep")
 void midPointStep(){
 solver = new MidPointSolver(dydx);
 StateInterface<Vector3dInterface> nextY;

 nextY = solver.step(dydx, 0, y, stepSize);

 printResults("MIDPOINT",exact1stepPos,exact1stepVel,nextY);;
 assertTrue(() -> Math.abs(exact1stepPos - nextY.get().get(0))<1e-4);
 assertTrue(() -> Math.abs(exact1stepVel - nextY.getRateOfChange().get().get(0))<1e-2);
 }
 @Test
 @DisplayName("rkStep")
 void rkStep(){
 solver = new RungeKuttaSolver(dydx);
 StateInterface<Vector3dInterface> nextY;

 nextY = solver.step(dydx, t, y, stepSize);

 printResults("RK",exact1stepPos,exact1stepVel,nextY);

 assertTrue(() -> Math.abs(exact1stepPos - nextY.get().get(0))<1e-2);
 assertTrue(() -> Math.abs(exact1stepVel - nextY.getRateOfChange().get().get(0))<1e-2);
 }
 @Test
 @DisplayName("eulerStep")
 void eulerStep(){
 solver = new EulerSolver(dydx);
 StateInterface<Vector3dInterface> nextY;

 nextY = solver.step(dydx, stepSize, y, stepSize);

 printResults("EULER",exact1stepPos,exact1stepVel,nextY);

 assertTrue(() -> Math.abs(exact1stepPos - nextY.get().get(0))<1e-2);
 assertTrue(() -> Math.abs(exact1stepVel - nextY.getRateOfChange().get().get(0))<1e-2);
 }
 @Test
 @DisplayName("StandardVerletStep")
 void verletStdStep(){
  solver = new StandardVerletSolver<>(dydx);
  StateInterface<Vector3dInterface> nextY;

  nextY = solver.step(dydx, stepSize, y, stepSize);

  printResults("StandardVerlet",exact1stepPos,exact1stepVel,nextY);

  assertTrue(() -> Math.abs(exact1stepPos - nextY.get().get(0))<1e-2);
  assertTrue(() -> Math.abs(exact1stepVel - nextY.getRateOfChange().get().get(0))<1e-2);
 }


 private void printResults(String solver, double exactSolutionPos,double exactSolutionVel, StateInterface<Vector3dInterface> y){
 System.out.println(solver+ " : " + y.get());
 System.out.println(solver+ " EXACT POS: "+ (exactSolutionPos - y.get().get(0)));
 System.out.println(solver+ " EXACT VEL: "+ (exactSolutionVel - y.getRateOfChange().get().get(0)));
 }
 private StateInterface<Vector3dInterface> solve(ODESolverInterface<Vector3dInterface> solver){
 StateInterface<Vector3dInterface> nextY;
        while(t<=tf+stepSize){
     nextY = solver.step(dydx, t, y, stepSize);
     y = nextY.copy();
     t+=stepSize;
     }
 return y;
 }

 }
