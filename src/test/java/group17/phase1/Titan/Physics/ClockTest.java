package group17.phase1.Titan.Physics;

import group17.phase1.Titan.System.Clock2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockTest {

    @Test
    @DisplayName("StepSize1000")
    void StepSize1000() {
        Clock2 clock = new Clock2().setLaunchDay();
        clock.step(1000);
        assertEquals(2020 + "/" + 4 + "/" + 1 + "/" + " " + 0 + ":" + 16 + ":" + 40, clock.toString());
    }

    @Test
    @DisplayName("StepSize1005")
    void StepSize1005() {
        Clock2 clock = new Clock2().setLaunchDay();
        clock.step(1005);
        assertEquals(2020+"/"+4+"/"+1+"/"+" "+0+":"+16+":"+45, clock.toString());
    }

    @Test
    @DisplayName("StepSizeDay")
    void StepSizeDay() {
        Clock2 clock = new Clock2().setLaunchDay();

        clock.step(86400);
        assertEquals(2020 + "/" + 4 + "/" + 2 + "/" + " " + 0 + ":" + 0 + ":" + 0, clock.toString());
    }

    @Test
    @DisplayName("StepSizeYear")
    void StepSizeYear() {
        Clock2 clock = new Clock2().setLaunchDay();

        clock.step(86400 * 365);
        assertEquals(2021 + "/" + 4 + "/" + 1 + "/" + " " + 0 + ":" + 0 + ":" + 0, clock.toString());
    }

    @Test
    @DisplayName("StepSizeDay")
    void ContinuouslyUpdate() {
        Clock2 clock = new Clock2().setLaunchDay();

        int[] daysInMonths = new int[]{30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 28, 31};
        double secInADay = 60 * 60 * 24; // 60sec * 60min * 24h
        double[] allMonths = new double[daysInMonths.length];


        int[] fromApril = new int[]{5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4};  //expected


        for (int i = 0; i < daysInMonths.length; i++) {
            allMonths[i] = secInADay * daysInMonths[i];  //step to take = (1day) * (days in this month)
        }


        for (int k = 0; k < 3; k++) //3 years
            for (int i = 0; i < allMonths.length; i++)  //12 months
            {
                System.out.println("taking step : " + allMonths[i]);
                clock.step(allMonths[i]);  //step 1 month
                assertEquals(fromApril[i], clock.getMonths());
            }
    }
}