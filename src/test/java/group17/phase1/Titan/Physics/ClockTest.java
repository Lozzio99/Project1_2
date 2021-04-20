package group17.phase1.Titan.Physics;

import group17.phase1.Titan.System.Clock;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ClockTest {

    @Test
    @DisplayName("StepSize1000")
    void StepSize1000() {
        Clock clock = new Clock();
        clock.setCurrentDate(2020,4,1,0,0,0);
        clock.step(1000);
        assertEquals(2020+"/"+4+"/"+1+"/"+" "+0+":"+16+":"+40, clock.toString());
    }

    @Test
    @DisplayName("StepSize1005")
    void StepSize1005() {
        Clock clock = new Clock();
        clock.setCurrentDate(2020,4,1,0,0,0);
        clock.step(1005);
        assertEquals(2020+"/"+4+"/"+1+"/"+" "+0+":"+16+":"+45, clock.toString());
    }

    @Test
    @DisplayName("StepSizeDay")
    void StepSizeDay() {
        Clock clock = new Clock();
        clock.setCurrentDate(2020,4,1,0,0,0);
        clock.step(86400);
        assertEquals(2020+"/"+4+"/"+2+"/"+" "+0+":"+0+":"+0, clock.toString());
    }

    @Test
    @DisplayName("StepSizeYear")
    void StepSizeYear() {
        Clock clock = new Clock();
        clock.setCurrentDate(2020,4,1,0,0,0);
        clock.step(86400*365);
        assertEquals(2021+"/"+4+"/"+1+"/"+" "+0+":"+0+":"+0, clock.toString());
    }
}