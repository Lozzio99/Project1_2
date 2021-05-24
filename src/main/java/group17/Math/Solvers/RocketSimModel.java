package group17.Math.Solvers;

import group17.Interfaces.*;
import group17.Math.Lib.Vector3D;
import group17.Simulation.Simulation;
import group17.System.SolarSystem;

import static group17.Main.simulation;
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

    static {
        DEFAULT_SOLVER = MIDPOINT_SOLVER; // put here the best solver
        STEP_SIZE = 50;
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
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Vector3D res1 = (Vector3D) stateFx(new Vector3D(-9.839198644659751E-01, -1.912460575714917E-01, 5.542821181619438E-05), new Vector3D(155580.29183892664,-133430.31623543188,-478.1753941877174), 86700.0 * 30 * 6);
        System.out.println(res1.toString());
        Vector3D res2 = (Vector3D) stateFx(new Vector3D(0.0, 0.0, 0.0), new Vector3D(100.1, 100.0, 100.0), 86700);
        System.out.println(res2.toString());

        // 1.363555710400778E+00, 3.201330747536073E-01, -2.693888301471804E-02
        // -2.1762284772885286E12,-2.590189915097415E12,-3.3437836480196583E10

    }

}
