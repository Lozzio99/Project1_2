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


    public void plan(Clock clock, Vector3dInterface decision) {
        Vector3dInterface v = this.shiftAtTime.put(clock, decision);
        if (v != null) this.shiftAtTime.put(clock, v.add(decision));
    }

    public void plan(StateInterface state, Vector3dInterface decision) {
        Vector3dInterface v = this.shiftAtLocation.put(state, decision);
        if (v != null) this.shiftAtLocation.put(state, v.add(decision));
    }

    public void plan(Vector3dInterface distance, Vector3dInterface decision) {
        Vector3dInterface v = this.shiftAtDistance.put(distance, decision);
        if (v != null) this.shiftAtDistance.put(distance, v.add(decision));
    }


    public void init() {
        this.shiftAtTime = new ConcurrentHashMap<>();
        this.shiftAtLocation = new ConcurrentHashMap<>();
        this.shiftAtDistance = new ConcurrentHashMap<>();
        //put all things here, even if they are later in the simulation
        //once we know them we just plan them here
        this.set(LAUNCH_DATE, new Vector3D(2.5, 2.0, 4.0));
    }


    public Vector3dInterface shift(SystemInterface system) {
        Vector3dInterface v = this.getDesiredVelocity(system.getClock()).
                add(this.getDesiredVelocity(system.systemState())).
                add(this.getDesiredVelocity(system.getRocket().getVectorLocation()/*TODO: titan.sub(rocket) */));
        return v;
        //eventually everything null this will return a vector (0,0,0)
    }

    public Vector3dInterface getDesiredVelocity(Clock clock) {
        if (clock == null) return new Vector3D();
        Vector3dInterface v = shiftAtTime.get(clock);
        return v == null ? new Vector3D() : v;
    }

    public Vector3dInterface getDesiredVelocity(StateInterface thisState) {
        if (thisState == null) return new Vector3D();
        Vector3dInterface v = shiftAtLocation.get(thisState);
        return v == null ? new Vector3D() : v;
    }

    public Vector3dInterface getDesiredVelocity(Vector3dInterface distanceToSomething) {
        if (distanceToSomething == null) return new Vector3D();
        Vector3dInterface v = shiftAtDistance.get(distanceToSomething);
        return v == null ? new Vector3D() : v;
    }


    public void set(Clock time, Vector3dInterface v) {
        Vector3dInterface vOld = this.shiftAtTime.put(time, v);
        if (vOld != null)
            this.plan(time, v);
    }

    public void set(StateInterface s, Vector3dInterface v) {
        this.shiftAtLocation.put(s, v);
    }

    public void set(Vector3dInterface d, Vector3dInterface v) {
        this.shiftAtDistance.put(d, v);
    }
}
