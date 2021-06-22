package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Math.Lib.Vector3D;
import group17.Simulation.Simulation;
import group17.System.SolarSystem;

import static group17.Main.simulation;
import static group17.Math.Solvers.NewtonRaphsonSolver.*;
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
        //System.out.println("Velocity "+ solution[solution.length - 1].getRateOfChange().getVelocities().get(11));
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
        //System.out.println("Velocity "+ solution[solution.length - 1].getRateOfChange().getVelocities().get(11));
        return solution[solution.length - 1].getPositions().get(11).clone();
    }

    public static Vector3dInterface stateFxCelestialBodies(double timeFinal, int id) {
        // init parameters
        SystemInterface system = createSystem(new Vector3D(0,0,0), new Vector3D(0,0,0));
        initialState = system.systemState().copy();
        solver = simulation.getUpdater().getSolver();

        // solve trajectory
        StateInterface[] solution = solver.solve(solver.getFunction(), initialState, timeFinal, STEP_SIZE);

        assert (simulation.getSystem().getCelestialBodies().size() > 10 &&
                !simulation.getSystem().getCelestialBodies().get(11).isCollided());

        return solution[solution.length - 1].getPositions().get(id).clone();
    }

    public static void main(String[] args) {
        Vector3dInterface res1 = stateFx(ROCKET_STARTING_POS, new Vector3D(5088.96749227992,-42683.62354900892,-2347.9074133259346),  1);
        //Vector3dInterface res2 = stateFxCelestialBodies(FixedTime1+FixedTime2, 8);
        Vector3dInterface res4 = stateFxCelestialBodies(1, 3);
        //Vector3dInterface res3 = res2.sub(res1);

        double res5 = res1.dist(res4) - (6.371e6+1e6);

        //double distance = res1.dist(res2);
        System.out.println(res1);
        //System.out.println(res2);
        //System.out.println(res3);
        //System.out.println(distance-(2575.5e3));
        System.out.println("Earth: "+res4);

        System.out.println("Distance: "+res5);
    }

}
