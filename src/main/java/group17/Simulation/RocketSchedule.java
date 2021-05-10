package group17.Simulation;

import group17.Interfaces.StateInterface;
import group17.Interfaces.SystemInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Vector3D;
import group17.System.Clock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static group17.Config.LAUNCH_DATE;

public class RocketSchedule {

    Map<Clock, Vector3dInterface> shiftAtTime;
    Map<StateInterface, Vector3dInterface> shiftAtLocation;
    Map<Vector3dInterface, Vector3dInterface> shiftAtDistance;

    public RocketSchedule() {
        this.shiftAtTime = new ConcurrentHashMap<>();
        this.shiftAtLocation = new ConcurrentHashMap<>();
        this.shiftAtDistance = new ConcurrentHashMap<>();
    }


    public void plan(Clock clock, Vector3dInterface decision) {
        Vector3dInterface v = this.shiftAtTime.put(clock, decision);
        if (v != null)
            this.shiftAtTime.put(clock, v.add(decision));
    }

    public void plan(StateInterface state, Vector3dInterface decision) {
        Vector3dInterface v = this.shiftAtLocation.put(state, decision);
        if (v != null)
            this.shiftAtLocation.put(state, v.add(decision));
    }

    public void plan(Vector3dInterface distance, Vector3dInterface decision) {
        Vector3dInterface v = this.shiftAtDistance.put(distance, decision);
        if (v != null)
            this.shiftAtDistance.put(distance, v.add(decision));
    }


    public void init() {
        //put all things here, even if they are later in the simulation
        //once we know them we just plan them here

        this.set(LAUNCH_DATE, new Vector3D(490, -838, -710));

    }


    public Vector3dInterface shift(SystemInterface system) {
        return this.getDesiredVelocity(system.getClock()).
                add(this.getDesiredVelocity(system.systemState())).
                add(this.getDesiredVelocity(system.getRocket().getVectorLocation()/*TODO: titan.sub(rocket) */));
        //eventually everything null this will return a vector (0,0,0)
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


    public void set(Clock launchDate, Vector3dInterface v) {
        this.shiftAtTime.put(launchDate, v);
    }

    public void set(StateInterface s, Vector3dInterface v) {
        this.shiftAtLocation.put(s, v);
    }

    public void set(Vector3dInterface d, Vector3dInterface v) {
        this.shiftAtDistance.put(d, v);
    }
}
