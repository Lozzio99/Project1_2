package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;
import group17.phase1.Titan.SolarSystem.SolarSystem;

import java.util.ArrayList;
import java.util.List;


public class SimulationRepository implements SimulationInterface, ODESolverInterface , ODEFunctionInterface, StateInterface, RateInterface
{
    SolarSystemInterface repository;
    GraphicsManager graphicsManager;
    List<Vector3dInterface> nextState;
    ODESolverInterface solver;
    ODEFunctionInterface gravity;

    public SimulationRepository()
    {
        this.repository = new SolarSystem();
        this.graphicsManager = new GraphicsManager();
        this.graphicsManager.startMainThread();
        this.nextState = new ArrayList<>();
        for (CelestialBody c : this.repository.getCelestialBodies())
            this.nextState.add(c.getVectorLocation());
    }

    @Override
    public SolarSystemInterface getSolarSystemRepository() {
        return this.repository;
    }


    @Override
    public GraphicsManager getGraphicsManager() {
        return this.graphicsManager;
    }

    @Override
    public ODESolverInterface getSolver() {
        return this;
    }

    @Override
    public ODEFunctionInterface getFunction() {
        return this;
    }


    /*
     * A class for solving a general differential equation dy/dt = f(t,y)
     *     y(t) describes the state of the system at time t
     *     f(t,y(t)) defines the derivative of y(t) with respect to time t
     */

    /*
     * Solve the differential equation by taking multiple steps.
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time
     * @return  an array of size ts.length with all intermediate states along the path
     */

    @Override
    public StateInterface[] solve(ODEFunctionInterface gravity, StateInterface stateAt0, double[] timeSteps)
    {
        StateInterface[] positions = new StateInterface[timeSteps.length];
        for (int i = 0; i< positions.length; i++){
            positions[i] = this.step(gravity,timeSteps[i],stateAt0,timeSteps.length/timeSteps[i]);
        }
        return positions;
    }


    /*
     * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
     * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   tf      the final time
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 including all intermediate states along the path
     */

    @Override
    public StateInterface[] solve(ODEFunctionInterface gravity, StateInterface stateAt0, double endTime, double timeSize)
    {
        StateInterface[] path = new StateInterface[(int)(Math.round(endTime/timeSize))+1];
        double time0 = 0;
        for (int i = 0; i< path.length-1;i++){
            path[i] = this.step(gravity,time0,stateAt0,timeSize);
            time0+= timeSize;
        }
        path[path.length-1] = this.step(gravity,endTime,stateAt0,timeSize);
        return path;
    }

    /*
     * Update rule for one step.
     *
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */

    @Override
    public StateInterface step(ODEFunctionInterface gravity, double timeAt, StateInterface stateAt, double timeSize)
    {
        return stateAt.addMul(timeSize,gravity.call(timeAt,stateAt));
    }


    /*
     * This is an interface for the function f that represents the
     * differential equation dy/dt = f(t,y).
     * You need to implement this function to represent to the laws of physics.
     *
     * For example, consider the differential equation
     *   dy[0]/dt = y[1];  dy[1]/dt=cos(t)-sin(y[0])
     * Then this function would be
     *   f(t,y) = (y[1],cos(t)-sin(y[0])).
     *
     * @param   t   the time at which to evaluate the function
     * @param   y   the state at which to evaluate the function
     * @return  The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     *
     *
     * ++++++++++++++ CALCULATE ATTRACTION ++++++++++++++++++++++*/

    @Override
    public RateInterface call(double timeAt, StateInterface stateAtTime)
    {

        for (int i = 0; i< this.getSolarSystemRepository().getCelestialBodies().size(); i++)
        {
            Vector3dInterface from = this.getSolarSystemRepository().getCelestialBodies().get(i).getVectorLocation();

            for (CelestialBody to : Main.simulation.getSolarSystemRepository().getCelestialBodies())
            {
                if (to.getVectorLocation() == from) continue;
                double sqrtDist  = from.dist(to.getVectorLocation());
                Vector3dInterface forceDir = Vector3D.unitVectorDistance(from,to.getVectorLocation());
                forceDir.mul(G);
                forceDir.mul(this.getSolarSystemRepository().getCelestialBodies().get(i).getMASS());
                forceDir.mul(to.getMASS());
                forceDir.div(sqrtDist);
                forceDir.div(this.getSolarSystemRepository().getCelestialBodies().get(i).getMASS());
                from.addMul(timeAt,forceDir);
            }
            this.nextState.set(i,from);
            //the state of the system now tends to somewhere else that is given in the shift vector
            //this applies to the whole simulation and it's applied to each body with respect to all
            //other bodies
        }
        this.setShiftVectors(nextState);
        return this;
    }


    /**
     * Update a state to a new state computed by: this + step * rate
     *
     * @param step   The time-step of the update
     * @param rate   The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     * @return The new state after the update. Required to have the same class as 'this'.
     */

    @Override
    public StateInterface addMul(double step, RateInterface rate) {
        for (int i = 0; i< this.repository.getCelestialBodies().size(); i++)
        {
            this.getSolarSystemRepository().getCelestialBodies().get(i).setShiftVector(rate.getShiftVectors().get(i));
            this.getSolarSystemRepository().getCelestialBodies().get(i).applyAttractionVector();
        }
        return this;
    }

    @Override
    public List<Vector3dInterface> getShiftVectors() {
        return this.nextState;
    }

    @Override
    public void setShiftVectors(List<Vector3dInterface> nextState) {
        this.nextState = nextState;
    }

    @Override
    public String toString(){
        return this.getSolarSystemRepository().getCelestialBodies().get(3).getVectorLocation().toString();
    }


}
