package group17.phase1.Titan.Graphics.Engine;

import group17.phase1.Titan.Bodies.SolarSystemInterface;
import group17.phase1.Titan.Physics.TimeSequence.SolverInterface;
import group17.phase1.Titan.Physics.Trajectories.FunctionInterface;

public class BodyLocationUpdater
{
    SolarSystemInterface solarSystem;
    SolverInterface solver;
    FunctionInterface movement;

    public BodyLocationUpdater(){
        //init variables..
        //..
        this.startSimulation();
    }
    void startSimulation(){

    }

    void updateLocations()
    {
        solarSystem.updateLocation(solarSystem.allCelestialBodies().get(0), solver);
        //...
    }
}
