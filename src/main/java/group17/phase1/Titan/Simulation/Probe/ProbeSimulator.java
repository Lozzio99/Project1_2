package group17.phase1.Titan.Simulation.Probe;

import group17.phase1.Titan.Interfaces.*;
import group17.phase1.Titan.Main;
import group17.phase1.Titan.Simulation.Vector3D;
import group17.phase1.Titan.SolarSystem.Bodies.CelestialBody;
import java.awt.*;

import static group17.phase1.Titan.Simulation.SimulationRepository.*;

public class ProbeSimulator extends CelestialBody implements ProbeSimulatorInterface
{

    ODESolverInterface gravity;
    ODEFunctionInterface movement;
    StateInterface system;

    public ProbeSimulator()
    {
        this.setMASS(15000);
        this.setRADIUS(1e8);
        this.setColour(Color.GREEN);
        this.setVectorLocation(Main.simulation.getBody("EARTH").getVectorLocation().clone());
        this.setVectorLocation(this.getVectorLocation().add(new Vector3D(-7485730.186,6281273.438,-8172135.798)));
        this.setVectorVelocity(Main.simulation.getBody("EARTH").getVectorVelocity().clone());
        this.setVectorVelocity(this.getVelocityVector().add(new Vector3D(17614.806943044582,-83146.44838398469,-0089.20608999642003)));

    }

    public void init(ODEFunctionInterface f, ODESolverInterface s, StateInterface state)
    {
        this.system = state;
        this.gravity = s;
        this.movement = f;
    }



    @Override
    public String toString() {
        return "PROBE";
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts)
    {
        endTime = ts[ts.length-1];
        stepSize = ts[0];
        currTime = 0;

        StateInterface[] path = new StateInterface[ts.length];
        Vector3dInterface[] traj = new Vector3dInterface[ts.length];

        for (int i = 0; i< ts.length; i++){
            path[i] = this.gravity.step(this.movement,ts[i],this.system,ts[i]-currTime);
            currTime = ts[i];
            traj[i] = this.system.getState().get(11).getVectorLocation();
        }

        return traj;
    }


    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double end, double timeSize)
    {
        endTime = end;
        stepSize = timeSize;
        StateInterface[] path = new StateInterface[(int)(Math.round(endTime/timeSize))+1];

        currTime = 0;
        Vector3dInterface[] traj = new Vector3dInterface[path.length];

        for (int i = 0; i< path.length-1;i++)
        {
            path[i] = this.gravity.step(this.movement,currTime,this.system,timeSize);
            currTime+= timeSize;
            traj[i] = this.system.getState().get(11).getVectorLocation();
        }
        path[path.length-1] = this.gravity.step(this.movement,endTime,this.system,endTime-currTime);
        traj[path.length-1] = this.system.getState().get(11).getVectorLocation();
        return traj;
    }

}
