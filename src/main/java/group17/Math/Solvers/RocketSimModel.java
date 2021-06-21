package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Math.Lib.Vector3D;
import group17.Simulation.Simulation;
import group17.System.SolarSystem;

import static group17.Main.simulation;
import static group17.Math.Solvers.NewtonRaphsonSolver.ROCKET_STARTING_POS;
import static group17.Utils.Config.*;

/**
 * The type Rocket sim model.
 */
public class RocketSimModel {
    /**
     * The constant targetPosition.
     */
    public static Vector3dInterface targetPosition;
    /**
     * The Solver.
     */

    static ODESolverInterface solver;
    /**
     * The Initial state.
     */
    static StateInterface initialState;

    /**
     * The constant pF.
     */

    public static NewtRaphFunction pF = vector -> {
        Vector3D aprxPos = (Vector3D) RocketSimModel.stateFx(NewtonRaphsonSolver.initPos, vector, NewtonRaphsonSolver.endTime);
        return targetPosition.sub(aprxPos);
    };

    /**
     * The constant pF.
     */

    public static NewtRaphFunction pFDelay = vector -> {
        Vector3D aprxPos = (Vector3D) RocketSimModel.stateFx(NewtonRaphsonSolver.initPos, vector, NewtonRaphsonSolver.endTime, NewtonRaphsonSolver.waitTime);
        return targetPosition.sub(aprxPos);
    };

    static {
        DEFAULT_SOLVER = VERLET_VEL_SOLVER; // put here the best solver
        STEP_SIZE = 1440;
        REPORT = false;
        INSERT_ROCKET = true;
        CHECK_COLLISIONS = false;
        ERROR_EVALUATION = false;
    }

    /**
     * Create system system interface.
     *
     * @param initLocation the init location
     * @param initVelocity the init velocity
     * @return the system interface
     */
    public static SystemInterface createSystem(Vector3dInterface initLocation, Vector3dInterface initVelocity) {
        final SystemInterface[] system = new SystemInterface[1];
        simulation = new Simulation() {
            @Override
            public void initSystem() {
                system[0] = new SolarSystem();
                system[0].initPlanets();
                system[0].initClock();
                system[0].initRocket();
                system[0].initialState();
                system[0].systemState().getPositions().set(11, initLocation);
                system[0].systemState().getRateOfChange().getVelocities().set(11, initVelocity);
            }

            @Override
            public SystemInterface getSystem() {
                return system[0];
            }
        };
        simulation.initSystem();
        simulation.initUpdater();
        return system[0];
    }


    /**
     * State fx vector 3 d interface.
     * Simulates position of the rocket with given parameters starting on 00:00:00 01/04/2020.
     *
     * @param initPos      the init pos
     * @param initVelocity the init velocity
     * @param timeFinal    the time final
     * @return the vector 3 d interface
     */
    public static Vector3dInterface stateFx(Vector3dInterface initPos, Vector3dInterface initVelocity, double timeFinal) {
        // init parameters
        SystemInterface system = createSystem(initPos, initVelocity);
        initialState = system.systemState().copy();
        solver = simulation.getUpdater().getSolver();

        // solve trajectory
        StateInterface[] solution = solver.solve(solver.getFunction(), initialState, timeFinal, STEP_SIZE);
        assert (simulation.getSystem().getCelestialBodies().size() > 10 &&
                !simulation.getSystem().getCelestialBodies().get(11).isCollided());
        return solution[solution.length - 1].getPositions().get(11).clone();
    }

    /**
     * State fx vector 3 d interface.
     * Simulates position of the rocket with given parameters starting after the delay and stops on final time.
     *
     * @param initPos      the init pos
     * @param initVelocity the init velocity
     * @param timeFinal    the time final
     * @param waitTime the delay time
     * @return the vector 3 d interface
     */
    public static Vector3dInterface stateFx(Vector3dInterface initPos, Vector3dInterface initVelocity, double timeFinal, double waitTime) {
        // init parameters
        SystemInterface system = createSystem(initPos, new Vector3D(0,0,0));
        initialState = system.systemState().copy();
        solver = simulation.getUpdater().getSolver();

        // solve trajectory
        StateInterface[] solution = solver.solve(solver.getFunction(), initialState, waitTime, STEP_SIZE);
        StateInterface init2State = solution[solution.length - 1].copy();

        init2State.getPositions().set(11, initPos);
        init2State.getRateOfChange().getVelocities().set(11, initVelocity);
        solution = solver.solve(solver.getFunction(), init2State, timeFinal-waitTime, STEP_SIZE);

        assert (simulation.getSystem().getCelestialBodies().size() > 10 &&
                !simulation.getSystem().getCelestialBodies().get(11).isCollided());
        return solution[solution.length - 1].getPositions().get(11).clone();
    }

    public static Vector3dInterface stateFxCelestialBodies(double timeFinal) {
        // init parameters
        SystemInterface system = createSystem(new Vector3D(0,0,0), new Vector3D(0,0,0));
        initialState = system.systemState().copy();
        solver = simulation.getUpdater().getSolver();

        // solve trajectory
        StateInterface[] solution = solver.solve(solver.getFunction(), initialState, timeFinal, STEP_SIZE);

        assert (simulation.getSystem().getCelestialBodies().size() > 10 &&
                !simulation.getSystem().getCelestialBodies().get(11).isCollided());
        return solution[solution.length - 1].getPositions().get(8).clone();
    }

    public static void main(String[] args) {
        Vector3dInterface res1 = stateFx(ROCKET_STARTING_POS, new Vector3D(5103.625210047158,-42679.20907496454,-2386.727992257258), 111153600);
        Vector3dInterface res2 = stateFxCelestialBodies(111153600);
        double distance = res2.dist(res1);
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(distance);
    }

}
