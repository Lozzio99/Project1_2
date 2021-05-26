package group17.Simulation.Rocket;

import group17.Interfaces.StateInterface;
import group17.Interfaces.SystemInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Lib.Vector3D;
import group17.System.Clock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static group17.Utils.Config.ERROR_EVALUATION;

/**
 * The type Rocket schedule.
 */
public class RocketSchedule {

    /**
     * The Shift at time.
     */
    Map<Clock, Vector3dInterface> shiftAtTime;
    /**
     * The Shift at location.
     */
    Map<StateInterface, Vector3dInterface> shiftAtLocation;
    /**
     * The Shift at distance.
     */
    Map<Vector3dInterface, Vector3dInterface> shiftAtDistance;

    Map<DecisionEvent, Vector3dInterface> shiftAtApproximatedState;


    /**
     * Init.
     */
    public void init() {
        this.shiftAtTime = new ConcurrentHashMap<>();
        this.shiftAtLocation = new ConcurrentHashMap<>();
        this.shiftAtDistance = new ConcurrentHashMap<>();
        //put all things here, even if they are later in the simulation
        //once we know them we just plan them here
    }

    /**
     * Prepare.
     */
    public void prepare() {
        final boolean f = ERROR_EVALUATION;
        ERROR_EVALUATION = false; // avoid making error reports
        //this.setPlan(new Clock().setLaunchDay(), new Vector3D(5000,5000,5000));
        //this.setPlan(new Clock().setInitialTime(0,0,0).setInitialTime(0,0,0),new Vector3D(0,0,0));
        ERROR_EVALUATION = f;
    }


    /**
     * Shift vector 3 d interface.
     *
     * @param system the system
     * @return the vector 3 d interface
     */
    public Vector3dInterface shift(SystemInterface system) {
        return this.getDesiredVelocity(system.getClock()).
                add(this.getDesiredVelocity(system.systemState())).
                add(this.getDesiredVelocity(system.systemState().getPositions().get(8).absSub(system.systemState().getPositions().get(11))));
        //eventually everything null this will return a vector (0,0,0)
    }

    /**
     * Gets desired velocity.
     *
     * @param clock the clock
     * @return the desired velocity
     */
    public Vector3dInterface getDesiredVelocity(Clock clock) {
        if (clock == null) return new Vector3D();
        Vector3dInterface v = shiftAtTime.getOrDefault(clock, new Vector3D());
        return v == null ? new Vector3D() : v;
    }

    /**
     * Gets desired velocity.
     *
     * @param thisState the this state
     * @return the desired velocity
     */
    public Vector3dInterface getDesiredVelocity(StateInterface thisState) {
        if (thisState == null) return new Vector3D();
        Vector3dInterface v = shiftAtLocation.get(thisState);
        return v == null ? new Vector3D() : v;
    }

    /**
     * Gets desired velocity.
     *
     * @param distanceToSomething the distance to something
     * @return the desired velocity
     */
    public Vector3dInterface getDesiredVelocity(Vector3dInterface distanceToSomething) {
        if (distanceToSomething == null) return new Vector3D();
        Vector3dInterface v = shiftAtDistance.get(distanceToSomething);
        return v == null ? new Vector3D() : v;
    }

    /**
     * Add to plan.
     *
     * @param clock    the clock
     * @param decision the decision
     */
    public void addToPlan(Clock clock, Vector3dInterface decision) {
        Vector3dInterface v = this.shiftAtTime.put(clock, decision);
        if (v != null) this.shiftAtTime.put(clock, v.add(decision));
    }

    /**
     * Add to plan.
     *
     * @param state    the state
     * @param decision the decision
     */
    public void addToPlan(StateInterface state, Vector3dInterface decision) {
        Vector3dInterface v = this.shiftAtLocation.put(state, decision);
        if (v != null) this.shiftAtLocation.put(state, v.add(decision));
    }

    /**
     * Add to plan.
     *
     * @param distance the distance
     * @param decision the decision
     */
    public void addToPlan(Vector3dInterface distance, Vector3dInterface decision) {
        Vector3dInterface v = this.shiftAtDistance.put(distance, decision);
        if (v != null) this.shiftAtDistance.put(distance, v.add(decision));
    }

    /**
     * Sets plan.
     *
     * @param time the time
     * @param v    the v
     */
    public void setPlan(Clock time, Vector3dInterface v) {
        if (time == null || v == null) return;
        Vector3dInterface vOld = this.shiftAtTime.remove(time);
        this.shiftAtTime.put(time, v);
    }

    /**
     * Sets plan.
     *
     * @param s the s
     * @param v the v
     */
    public void setPlan(StateInterface s, Vector3dInterface v) {
        if (s == null || v == null) return;
        this.shiftAtLocation.put(s, v);
    }

    /**
     * Sets plan.
     *
     * @param d the d
     * @param v the v
     */
    public void setPlan(Vector3dInterface d, Vector3dInterface v) {
        if (d == null || v == null) return;
        this.shiftAtDistance.put(d, v);
    }
}
