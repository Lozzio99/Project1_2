package group17.phase1.Titan.Simulation.Probe;

import group17.phase1.Titan.Physics.SolarSystem.CelestialBody;
import group17.phase1.Titan.Physics.SolarSystem.ProbeSimulator;
import group17.phase1.Titan.interfaces.ProbeSimulatorInterface;
import group17.phase1.Titan.interfaces.Vector3dInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ProbeSimulatorTest extends CelestialBody implements ProbeSimulatorInterface
{

    ProbeSimulator test;
    ProbeSimulatorTest(){
        test = new ProbeSimulator();
    }


    @Override
    public String toString() {
        return "PROBE";
    }

    @Override
    public void initPosition() {

    }


    @Test
    @DisplayName("TestSetMASS")
    void TestSetMASS()
    {
        test.setMASS(0);
        assert test.getMASS() == 0;
    }

    @Test
    @DisplayName("TestGetMASS")
    void TestGetMASS() {
        test.setMASS(0);
        Assertions.assertEquals(0,test.getMASS());
    }


    @Test
    @DisplayName("TestSetColour")
    void TestSetColour() {
    }

    @Test
    @DisplayName("TestSetRADIUS")
    void TestSetRADIUS() {
    }

    @Test
    @DisplayName("TestSetDENSITY")
    void TestSetDENSITY() {
    }

    @Test
    @DisplayName("TestSetX_ROTATION")
    void TestSetX_ROTATION() {
    }

    @Test
    @DisplayName("TestSetY_ROTATION")
    void TestSetY_ROTATION() {
    }

    @Test
    @DisplayName("TestSetZ_ROTATION")
    void TestSetZ_ROTATION() {
    }

    @Test
    @DisplayName("TestSetVectorLocation")
    void TestSetVectorLocation() {
    }

    @Test
    @DisplayName("TestSetVectorVelocity")
    void TestSetVectorVelocity() {
    }





    @Test
    @DisplayName("TestGetRADIUS")
    void TestGetRADIUS() {
    }

    @Test
    @DisplayName("TestGetDensity")
    void TestGetDensity() {
    }

    @Test
    @DisplayName("TestGetX_ROTATION")
    void TestGetX_ROTATION() {
    }

    @Test
    @DisplayName("TestGetY_ROTATION")
    void TestGetY_ROTATION() {
    }

    @Test
    @DisplayName("TestGetZ_ROTATION")
    void TestGetZ_ROTATION() {
    }

    @Test
    @DisplayName("TestGetVectorLocation")
    void TestGetVectorLocation() {
    }

    @Test
    @DisplayName("TestGetVectorVelocity")
    void TestGetVectorVelocity() {
    }

    @Test
    @DisplayName("TestGetColour")
    void TestGetColour() {

    }

    @Test
    @DisplayName("TestAddToPath")
    void TestAddToPath() {
    }

    @Test
    @DisplayName("TestGetPath")
    void TestGetPath() {
    }

    @Test
    @DisplayName("TestEquals")
    void TestEquals() {
    }

    @Test
    @DisplayName("TestHashCode")
    void TestHashCode() {
    }

    @Test
    @DisplayName("Init")
    void Init() {
    }

    @Test
    @DisplayName("TestToString")
    void TestToString() {
    }

    @Test
    @DisplayName("Trajectory")
    void Trajectory() {
    }

    @Test
    @DisplayName("TestTrajectory")
    void TestTrajectory() {
    }


    @Override
    @Test
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        return new Vector3dInterface[0];
    }

    @Override
    @Test
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        return new Vector3dInterface[0];
    }
}