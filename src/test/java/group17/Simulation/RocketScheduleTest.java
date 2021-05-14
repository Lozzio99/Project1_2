package group17.Simulation;

import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import group17.Math.Vector3D;
import group17.System.Bodies.CelestialBody;
import group17.System.Clock;
import group17.System.SystemState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;

class RocketScheduleTest {

    RocketSchedule scheduler;

    public static StateInterface createTestState() {
        CelestialBody body1 = new CelestialBody() {
            @Override
            public String toString() {
                return "body1";
            }

            @Override
            public void initProperties() {
                this.setVectorLocation(new Vector3D(2, 2, 2));
                this.setVectorVelocity(new Vector3D(200, 200, 200));
            }
        };
        CelestialBody body2 = new CelestialBody() {
            @Override
            public String toString() {
                return "body2";
            }

            @Override
            public void initProperties() {
                this.setVectorLocation(new Vector3D(3, 3, 3));
                this.setVectorVelocity(new Vector3D(300, 300, 300));
            }
        };
        return new SystemState().state0(List.of(body1, body2));
    }

    @BeforeEach
    void setUp() {
        scheduler = new RocketSchedule();
        scheduler.init();
    }

    @Test
    @DisplayName("Init")
    void Init() {
        scheduler = null;
        scheduler = new RocketSchedule();
        assertNotNull(scheduler);
        scheduler.init();
        assertNotNull(scheduler);
        assertNotNull(scheduler.shiftAtTime);
        assertNotNull(scheduler.shiftAtDistance);
        assertNotNull(scheduler.shiftAtLocation);
        assertAll(
                () -> assertEquals(0, scheduler.shiftAtTime.size()),
                () -> assertEquals(0, scheduler.shiftAtDistance.size()),
                () -> assertEquals(0, scheduler.shiftAtLocation.size())
        );
    }

    @Test
    @DisplayName("Prepare")
    void Prepare() {
        scheduler.prepare();
        assertTrue(scheduler.shiftAtTime.containsKey(new Clock().setLaunchDay()));
        assertAll(() -> {
                    for (Map.Entry<?, ?> entry : scheduler.shiftAtTime.entrySet()) {
                        assertEquals(Clock.class, entry.getKey().getClass());
                        assertEquals(Vector3D.class, entry.getValue().getClass());
                    }
                },
                () -> {
                    for (Map.Entry<?, ?> entry : scheduler.shiftAtDistance.entrySet()) {
                        assertEquals(Vector3D.class, entry.getKey().getClass());
                        assertEquals(Vector3D.class, entry.getValue().getClass());
                    }
                },
                () -> {
                    for (Map.Entry<?, ?> entry : scheduler.shiftAtLocation.entrySet()) {
                        assertEquals(Vector3D.class, entry.getKey().getClass());
                        assertEquals(Vector3D.class, entry.getValue().getClass());
                    }
                });

        //TO BE FURTHER IMPLEMENTED

    }


    @Test
    @DisplayName("Plan")
    void addPlanByClock() {
        scheduler.prepare();
        Clock clock = new Clock().setLaunchDay();
        Vector3dInterface v = scheduler.getDesiredVelocity(clock);
        assertFalse(v.isZero());
        Vector3dInterface h = new Vector3D(100, 100, 100);
        scheduler.addToPlan(clock, h);
        scheduler.addToPlan(new Clock()               //try to confuse him
                        .setInitialDay(100, 100, 100)
                        .setInitialTime(1000, 10000, 1000),
                new Vector3D(NaN, NaN, NaN)); // shouldn't be counted
        Vector3dInterface result = scheduler.getDesiredVelocity(clock);
        assertNotEquals(v, result);
        assertEquals(v.add(h), result);
    }

    @Test
    @DisplayName("TestPlan")
    void addPlanByState() {
        StateInterface state = createTestState();
        scheduler.setPlan(state, new Vector3D(200, 200, 200));
        Vector3dInterface prev = scheduler.getDesiredVelocity(state);
        scheduler.addToPlan(state, new Vector3D(-200, -200, -200));
        scheduler.addToPlan(new SystemState(), new Vector3D(NaN, NaN, NaN));
        Vector3dInterface result = scheduler.getDesiredVelocity(state);
        assertAll(
                () -> assertEquals(new Vector3D(200, 200, 200), prev),
                () -> assertNotEquals(prev, result),
                () -> assertNotEquals(new Vector3D(NaN, NaN, NaN), result),
                () -> assertNotEquals(new Vector3D(NaN, NaN, NaN), prev),
                () -> assertEquals(new Vector3D(), result)  //(0,0,0)
        );
    }


    @Test
    @DisplayName("TestPlan1")
    void addPlanByDistance() {
        Vector3dInterface fixedKey = new Vector3D(10, 10, 10);
        Vector3dInterface fixedValue = new Vector3D(100, 100, 100);
        scheduler.addToPlan(fixedKey, fixedValue);
        Vector3dInterface prev = scheduler.getDesiredVelocity(fixedKey);
        for (int i = 0; i < 9; i++) {  //was already 100
            scheduler.addToPlan(fixedKey, fixedValue);
        }
        //confuse him
        scheduler.addToPlan(new Vector3D(-100, -100, -100), new Vector3D(NaN, NaN, NaN));

        Vector3dInterface expected = fixedValue.mul(10);
        Vector3dInterface result = scheduler.getDesiredVelocity(fixedKey);
        assertAll(
                () -> assertEquals(new Vector3D(100, 100, 100), prev),
                () -> assertNotEquals(prev, result),
                () -> assertEquals(expected, result),
                () -> assertEquals(new Vector3D(1000, 1000, 1000), result),
                () -> assertNotEquals(new Vector3D(NaN, NaN, NaN), scheduler.getDesiredVelocity(fixedKey))
        );
    }


    @Test
    @DisplayName("Shift")
    void Shift() {

    }

    @Test
    @DisplayName("GetDesiredVelocityByClock")
    void GetDesiredVelocityByClock() {
        Clock mb = new Clock()
                .setInitialDay(1, 7, 1999)
                .setInitialTime(0, 0, 0);
        scheduler.setPlan(mb, new Vector3D(100, 100, 100));
        Vector3dInterface v1, v2, v3;
        v1 = scheduler.getDesiredVelocity(mb);
        scheduler.setPlan(mb, new Vector3D(-100, -100, -100));
        v2 = scheduler.getDesiredVelocity(mb);
        assertDoesNotThrow(() -> scheduler.getDesiredVelocity((Clock) null));
        v3 = scheduler.getDesiredVelocity((Clock) null);
        assertAll(
                () -> assertEquals(new Vector3D(100, 100, 100), v1),
                () -> assertEquals(new Vector3D(-100, -100, -100), v2),
                () -> assertNotNull(v3),
                () -> assertEquals(new Vector3D(), v3)
        );
    }

    @Test
    @DisplayName("GetDesiredVelocityByState")
    void GetDesiredVelocityByState() {
        StateInterface state = createTestState();
        scheduler.setPlan(state, new Vector3D(100, 100, 100));
        Vector3dInterface v1, v2, v3;
        v1 = scheduler.getDesiredVelocity(state);
        state.getPositions().set(0, new Vector3D(4, 4, 4)); //override the hashcode
        scheduler.setPlan(state, new Vector3D(-100, -100, -100));
        v2 = scheduler.getDesiredVelocity(state);
        v3 = scheduler.getDesiredVelocity((StateInterface) null);
        assertAll(
                () -> assertEquals(new Vector3D(100, 100, 100), v1),
                () -> assertEquals(new Vector3D(-100, -100, -100), v2),
                () -> assertNotNull(v3),
                () -> assertEquals(new Vector3D(), v3)
        );
    }

    @Test
    @DisplayName("GetDesiredVelocityByDistance")
    void GetDesiredVelocityByDistance() {
        Vector3dInterface key1 = new Vector3D(10, 10, 10);
        Vector3dInterface value1 = new Vector3D(100, 100, 100);
        scheduler.setPlan(key1, value1);
        for (int i = 1; i < 10; i++) {
            Vector3dInterface v1 = scheduler.getDesiredVelocity(key1);
            scheduler.setPlan(key1.mul(i) /* does not effect the vector object */, value1);
            assertEquals(value1, v1);
            assertEquals(value1, scheduler.getDesiredVelocity(key1.mul(i)));
            assertEquals(new Vector3D(), scheduler.getDesiredVelocity((Vector3dInterface) null));
        }
    }

    @Test
    @DisplayName("Set")
    void setPlanByClock() {
        Clock mb = new Clock()
                .setInitialDay(1, 7, 1999)
                .setInitialTime(0, 0, 0);
        scheduler.setPlan(mb, new Vector3D(100, 100, 100));
        Vector3dInterface v1, v2, v3;
        v1 = scheduler.getDesiredVelocity(mb);
        assertEquals(new Vector3D(100, 100, 100), v1);
        scheduler.setPlan(mb, new Vector3D(-100, -100, -100));
        v2 = scheduler.getDesiredVelocity(mb);
        assertNotEquals(v1, v2);
        assertEquals(new Vector3D(-100, -100, -100), v2);
        scheduler.setPlan(mb, new Vector3D(-300, -300, -300));
        v3 = scheduler.getDesiredVelocity(mb);
        assertNotEquals(v1, v3);
        assertNotEquals(v2, v3);
        assertEquals(new Vector3D(-300, -300, -300), v3);
        assertDoesNotThrow(() -> scheduler.setPlan((Clock) null, null));
        assertDoesNotThrow(() -> scheduler.setPlan(mb, null));
        assertNotNull(scheduler.getDesiredVelocity(mb));
        assertEquals(v3, scheduler.getDesiredVelocity(mb));
    }

    @Test
    @DisplayName("TestSet")
    void setPlanByState() {
        StateInterface state = createTestState();
        scheduler.setPlan(state, new Vector3D(100, 100, 100));
        Vector3dInterface v1, v2, v3;
        v1 = scheduler.getDesiredVelocity(state);
        state.getPositions().set(0, new Vector3D(4, 4, 4)); //override the hashcode
        scheduler.setPlan(state, new Vector3D(-100, -100, -100));
        v2 = scheduler.getDesiredVelocity(state);
        assertAll(
                () -> assertNotEquals(v1, v2),
                () -> assertEquals(new Vector3D(100, 100, 100), v1),
                () -> assertEquals(new Vector3D(-100, -100, -100), v2)
        );
        scheduler.setPlan(state, new Vector3D(-300, -300, -300));
        v3 = scheduler.getDesiredVelocity(state);
        assertAll(
                () -> assertNotEquals(v1, v3),
                () -> assertNotEquals(v2, v3),
                () -> assertEquals(new Vector3D(-300, -300, -300), v3),
                () -> assertDoesNotThrow(() -> scheduler.setPlan((StateInterface) null, null))
        );
    }

    @Test
    @DisplayName("TestSet1")
    void setPlanByDistance() {
        Vector3dInterface key1 = new Vector3D(10, 10, 10);
        Vector3dInterface value1 = new Vector3D(100, 100, 100);
        scheduler.setPlan(key1, value1);
        for (int i = 2; i < 10; i++) {
            Vector3dInterface v1 = scheduler.getDesiredVelocity(key1);
            assertEquals(new Vector3D(100, 100, 100), v1);
            scheduler.setPlan(key1.mul(i) /* does not effect the vector object */, value1.mul(i));
            Vector3dInterface v2 = scheduler.getDesiredVelocity(key1.mul(i));
            assertNotEquals(v1, v2);
            assertEquals(value1.mul(i), v2); //just a check
        }

        assertAll(
                () -> assertDoesNotThrow(() -> scheduler.setPlan((Vector3dInterface) null, null)),
                () -> assertDoesNotThrow(() -> scheduler.setPlan((Vector3dInterface) null, new Vector3D(10000, 10000, 10000))),
                () -> {
                    Vector3dInterface test = scheduler.getDesiredVelocity((Vector3dInterface) null);
                    assertEquals(new Vector3D(), test);
                }
        );

    }


}