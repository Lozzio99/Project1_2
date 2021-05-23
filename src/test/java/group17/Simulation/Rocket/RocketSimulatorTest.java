package group17.Simulation.Rocket;

import group17.Math.Lib.Vector3D;
import group17.Simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static group17.Main.simulation;
import static group17.Utils.Config.*;
import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class RocketSimulatorTest {

    @BeforeEach
    void init() {
        ERROR_EVALUATION = false;
        CHECK_COLLISIONS = false;
        REPORT = false;
        DEBUG = false;
        ENABLE_GRAPHICS = LAUNCH_ASSIST = false;
        INSERT_ROCKET = true;

        simulation = new Simulation();
        simulation.initSystem();
    }

    @Test
    @DisplayName("AddAcceleration")
    void AddAcceleration() {

        RocketSimulator rocket = new RocketSimulator();
        rocket.initProperties();
    }

    @Test
    @DisplayName("GetLocalAcceleration")
    void GetLocalAcceleration() {
    }

    @Test
    @DisplayName("SetLocalAcceleration")
    void SetLocalAcceleration() {
    }

    @Test
    @DisplayName("EvaluateLoss")
    void EvaluateLoss() {

        RocketSimulator rocket = new RocketSimulator();
        rocket.initProperties();
        assertEquals(0, rocket.evaluateLoss(rocket.getVectorVelocity(), rocket.getVectorVelocity()));
        assumeTrue(!rocket.getVectorVelocity().isZero());
        assertNotEquals(0, rocket.evaluateLoss(new Vector3D(), rocket.getVectorVelocity()));
    }

    @Test
    @DisplayName("UpdateMass")
    void UpdateMass() {
        RocketSimulator rocket = new RocketSimulator();
        rocket.initProperties();
        double mass = rocket.getMASS();
        assumeTrue(!rocket.getVectorVelocity().isZero());
        rocket.updateMass(rocket.evaluateLoss(new Vector3D(), rocket.getVectorVelocity()));
        assertNotEquals(mass, rocket.getMASS());
    }

    @Test
    @DisplayName("GetFuelMass")
    void GetFuelMass() {
        RocketSimulator rocket = new RocketSimulator();
        rocket.initProperties();
        double fuelMass = rocket.getFuelMass();
        assertNotEquals(0, fuelMass);
        rocket.updateMass(fuelMass);
        assertNotEquals(fuelMass, rocket.getFuelMass());
    }

    @Test
    @DisplayName("SetFuelMass")
    void SetFuelMass() {
        RocketSimulator rocket = new RocketSimulator();
        rocket.setFuelMass(NaN);
        assertEquals(NaN, rocket.getFuelMass());
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
        assertEquals("ROCKET", new RocketSimulator().toString());
    }

    @Test
    @DisplayName("Info")
    void Info() {
        RocketSimulator rocket = new RocketSimulator();
        rocket.initProperties();
        assertEquals("Dry Mass: 98000.0\n" +
                "Fuel Mass: 20000.0\n", rocket.info());
    }

    @Test
    @DisplayName("Update")
    void Update() {

    }

    @Test
    @DisplayName("InitProperties")
    void InitProperties() {
    }

}