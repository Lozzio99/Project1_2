package Module.System.Rocket;

import Module.Math.NewtRaphFunction;
import Module.Math.Solvers.NewtonRaphsonSolver;
import Module.Math.Solvers.ODESolverInterface;
import Module.Math.Vector3D;
import Module.Math.Vector3dInterface;
import Module.System.State.StateInterface;
import Module.System.SystemInterface;

import static Module.Config.STEP_SIZE;
import static Module.Main.simulation;

/**
 * The type Rocket sim model.
 */
public class RocketSimulatorModel {
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
        Vector3D aprxPos = (Vector3D) RocketSimulatorModel.stateFx(NewtonRaphsonSolver.initPos, vector, NewtonRaphsonSolver.endTime);
        return targetPosition.sub(aprxPos);
    };

    /**
     * The constant pF.
     */

    public static NewtRaphFunction pFDelay = vector -> {
        Vector3D aprxPos = (Vector3D) RocketSimulatorModel.stateFx(NewtonRaphsonSolver.initPos, vector, NewtonRaphsonSolver.endTime, NewtonRaphsonSolver.waitTime);
        return targetPosition.sub(aprxPos);
    };


    /**
     * Create system system interface.
     *
     * @param initLocation the init location
     * @param initVelocity the init velocity
     * @return the system interface
     */
    public static SystemInterface createSystem(Vector3dInterface initLocation, Vector3dInterface initVelocity) {
        final SystemInterface[] system = new SystemInterface[1];


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
        initialState = system.getState().copy();
        solver = simulation.getRunner().getSolver();

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
     * @param waitTime     the delay time
     * @return the vector 3 d interface
     */
    public static Vector3dInterface stateFx(Vector3dInterface initPos, Vector3dInterface initVelocity, double timeFinal, double waitTime) {
        // init parameters
        SystemInterface system = createSystem(initPos, new Vector3D(0, 0, 0));
        initialState = system.getState().copy();
        solver = simulation.getRunner().getSolver();

        // solve trajectory
        StateInterface[] solution = solver.solve(solver.getFunction(), initialState, waitTime, STEP_SIZE);
        StateInterface init2State = solution[solution.length - 1].copy();

        init2State.getPositions().set(11, initPos);
        init2State.getRateOfChange().getPositions().set(11, initVelocity);
        solution = solver.solve(solver.getFunction(), init2State, timeFinal - waitTime, STEP_SIZE);

        assert (simulation.getSystem().getCelestialBodies().size() > 10 &&
                !simulation.getSystem().getCelestialBodies().get(11).isCollided());
        return solution[solution.length - 1].getPositions().get(11).clone();
    }

}
