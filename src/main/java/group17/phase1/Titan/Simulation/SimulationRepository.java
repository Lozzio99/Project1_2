package group17.phase1.Titan.Simulation;

import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;
import group17.phase1.Titan.SolarSystem.SolarSystem;
import static group17.phase1.Titan.Utils.Configuration.*;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Interfaces.RateInterface.G;


public class SimulationRepository implements SimulationInterface, ODESolverInterface, ODEFunctionInterface
{
    SolarSystemInterface solarSystem;
    GraphicsManager graphicsManager;

    static int stepSize = 1000;
    double currTime = 0;
    double endTime = Double.MAX_VALUE;
    RateInterface rateOfChange;
    StateInterface solarSystemState;

    public SimulationRepository()
    {
        this.solarSystem = new SolarSystem();
        this.graphicsManager = new GraphicsManager();
        solarSystemState = (StateInterface) solarSystem;
        rateOfChange = (RateInterface) solarSystem;
        this.graphicsManager.init();
        this.graphicsManager.waitForStart();
    }

    @Override
    public SolarSystemInterface getSolarSystemRepository() {
        return this.solarSystem;
    }


    @Override
    public GraphicsManager getGraphicsManager() {
        return this.graphicsManager;
    }

    @Override
    public void runSimulation() {
        for (int i = 0; i < endTime; i++) {
            if (DEBUG)System.out.println("Earth Pos: " + solarSystem.getCelestialBodies().get(3).getVectorLocation().toString());
            if (DEBUG)System.out.println("Earth Vel: " + solarSystem.getCelestialBodies().get(3).getVelocityVector().toString());

            this.step(this, currTime, this.solarSystemState, stepSize); // The calculation

            try {
                Thread.sleep(1);
            }
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (DEBUG) System.out.println("Time: " + currTime);
            currTime += stepSize;
        }

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

        //[4,4,4,4,4,4,4,2];
        //[time0,time4,time8,.....]
        StateInterface[] path = new StateInterface[(int)(Math.round(endTime/timeSize))+1];
        double time0 = 0;
        for (int i = 0; i< path.length-1;i++){
            path[i] = this.step(gravity,time0,stateAt0,timeSize);
            time0+= timeSize;
        }
        path[path.length-1] = this.step(gravity,endTime,stateAt0,timeSize);
        return path;
    }


    /**
     * Update rule for one step.
     * @param   gravity   the function defining the differential equation dy/dt=f(t,y)
     * @param   timeAt   the time
     * @param   stateAt   the state
     * @param   timeSize   the step size
     * @return  the new state after taking one step
     */
    @Override
    public StateInterface step(ODEFunctionInterface gravity, double timeAt, StateInterface stateAt, double timeSize)
    {
        return stateAt.addMul(timeAt,gravity.call(timeSize,stateAt));
    }


    @Override
    public String toString()
    {
        return this.getSolarSystemRepository().getCelestialBodies().get(3).getVectorLocation().toString();
    }


    @Override
    public RateInterface call(double timeAt, StateInterface stateAtTime)
    {
        for (CelestialBody thisBody : this.solarSystem.getCelestialBodies())
        {

            Vector3dInterface totalAcceleration = new Vector3D(0,0,0);
            for (CelestialBody otherBody : this.solarSystem.getCelestialBodies()){
                if (thisBody!= otherBody){
                    Vector3dInterface acc = new Vector3D(thisBody.getVectorLocation().getX(), thisBody.getVectorLocation().getY(), thisBody.getVectorLocation().getZ());
                    double squareDist = Math.pow(thisBody.getVectorLocation().dist(otherBody.getVectorLocation()), 2);
                    acc.sub(otherBody.getVectorLocation()); // Get the force vector
                    acc.mul(1 / Math.sqrt(squareDist)); // Normalize to 1
                    acc.mul((G * otherBody.getMASS()) / squareDist); // Convert force to acceleration
                    totalAcceleration.addMul(timeAt, acc);
                }
            }
            thisBody.getVelocityVector().add(totalAcceleration);
        }
        return this.rateOfChange;
    }
}
