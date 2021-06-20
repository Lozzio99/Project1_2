package phase3.Rocket.Optimisation;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Solvers.ODESolverInterface;
import phase3.Simulation.Simulation;
import phase3.Simulation.SimulationInterface;
import phase3.System.SolarSystem;
import phase3.System.State.StateInterface;
import phase3.System.SystemInterface;

import static phase3.Config.*;
import static phase3.Main.simulation;

/**
 * The type Rocket sim model.
 */
public class RocketSimulatorModel {
    /**
     * The constant targetPosition.
     */
    public static Vector3dInterface targetPosition;
    /**
     * The constant pF.
     */

    public static ObjectiveFunction pFDelay = vector -> {
        Vector3D aprxPos = new Vector3D();//(Vector3D) RocketSimulatorModel.stateFx(NewtonRaphsonSolver.initPos, vector, NewtonRaphsonSolver.endTime, NewtonRaphsonSolver.waitTime);
        return targetPosition.sub(aprxPos);
    };
    /**
     * The Solver.
     */

    static ODESolverInterface<Vector3dInterface> solver;

    /**
     * The constant pF.
     */

    public static ObjectiveFunction pF = vector -> {
        Vector3dInterface aprxPos = RocketSimulatorModel.stateFx(NewtonRaphsonSolver.initPos, vector, NewtonRaphsonSolver.endTime);
        return targetPosition.sub(aprxPos);
    };
    /**
     * The Initial state.
     */
    static StateInterface<Vector3dInterface> initialState;


    /**
     * Create system system interface.
     *
     * @param initLocation the init location
     * @param initVelocity the init velocity
     * @return the system interface
     */
    public static SystemInterface createSystem(Vector3dInterface initLocation, Vector3dInterface initVelocity) {
        final SystemInterface system = new SolarSystem();
        system.init();
        int length = system.getState().get().length;
        system.getState().get()[11].set(initLocation);
        system.getState().get()[11+length/2].set(initVelocity);
        return system;
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
        SIMULATION = FLIGHT_TO_TITAN;
        SystemInterface system = createSystem(initPos, initVelocity);
        initialState = system.getState();
        //SimulationInterface simulationN = new Simulation();
        simulation = new Simulation();
        simulation.init();
        solver = simulation.getRunner().getSolver();

        solver.step(solver.getFunction(), 0.0, initialState, NEWTON_STEP_SIZE);
        // solve trajectory
        StateInterface<Vector3dInterface>[] solution = solver.solve(solver.getFunction(), initialState, timeFinal, NEWTON_STEP_SIZE);
        assert (simulation.getSystem().getCelestialBodies().size() > 10 &&
                !simulation.getSystem().getCelestialBodies().get(11).isCollided());
        return (Vector3dInterface) solution[solution.length - 1];
    }

    /**
     * State fx vector 3 d interface.
     * Simulates position of the rocket with given parameters starting after the delay and stops on final time.
     *
     * @param initPos      the init pos
     * @param initVelocity the init velocity
     * @param timeFinal    the time final
     * @param waitTime     the delay time
     * @return the vector 3 d interface
     */
    /*
    public static Vector3dInterface stateFx(Vector3dInterface initPos, Vector3dInterface initVelocity, double timeFinal, double waitTime) {
        // init parameters
        SystemInterface system = createSystem(initPos, new Vector3D(0, 0, 0));
        initialState = system.getState();
        solver = simulation.getRunner().getSolver();

        // solve trajectory
        StateInterface[] solution = solver.solve(solver.getFunction(), initialState, waitTime, MODULE_STEP_SIZE);
        StateInterface init2State = solution[solution.length - 1].copy();

        init2State.getPositions().set(11, initPos);
        init2State.getRateOfChange().getPositions().set(11, initVelocity);
        solution = solver.solve(solver.getFunction(), init2State, timeFinal - waitTime, MODULE_STEP_SIZE);

        assert (simulation.getSystem().getCelestialBodies().size() > 10 &&
                !simulation.getSystem().getCelestialBodies().get(11).isCollided());
        return solution[solution.length - 1].getPositions().get(11).clone();
    }

     */

}
