package group17.Simulation;

import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Vector3D;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

public class RocketSchedule {

    Map<Clock, Vector3dInterface> shiftAtTime;
    Map<StateInterface, Vector3dInterface> shiftAtLocation;
    Map<Vector3dInterface, Vector3dInterface> shiftAtDistance;

    public RocketSchedule() {
        this.shiftAtTime = new HashMap<>();
        this.shiftAtLocation = new HashMap<>();
        this.shiftAtDistance = new HashMap<>();
    }


    public void plan(Clock clock, Vector3dInterface decision) {
        this.shiftAtTime.put(clock, decision);
    }

    public void plan(StateInterface state, Vector3dInterface decision) {
        this.shiftAtLocation.put(state, decision);
    }

    public void plan(Vector3dInterface distance, Vector3dInterface decision) {
        this.shiftAtDistance.put(distance, decision);
    }


    public Vector3dInterface getDesiredVelocity(Clock clock) {
        return shiftAtTime.getOrDefault(clock, new Vector3D());
    }

    public Vector3dInterface getDesiredVelocity(StateInterface thisState) {
        return shiftAtLocation.getOrDefault(thisState, new Vector3D());
    }

    public Vector3dInterface getDesiredVelocity(Vector3dInterface distanceToSomething) {
        return shiftAtDistance.getOrDefault(distanceToSomething, new Vector3D());
    }
}
