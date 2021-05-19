package group17;

import group17.System.Clock;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockTest {

    static {
        Config.ERROR_EVALUATION = false;
    }

    @Test
    @DisplayName("StepSize1000")
    void StepSize1000() {
        Clock clock = new Clock().setLaunchDay();
        clock.step(1000);
        assertEquals("Clock { [ 00 : 16 : 40 ]     01 / 04 / 2020  }", clock.toString());
    }

    @Test
    @DisplayName("StepSize1005")
    void StepSize1005() {
        Clock clock = new Clock().setLaunchDay();
        clock.step(1005);
        assertEquals("Clock { [ 00 : 16 : 45 ]     01 / 04 / 2020  }", clock.toString());
    }

    @Test
    @DisplayName("StepSizeDay")
    void StepSizeDay() {
        Clock clock = new Clock().setLaunchDay();

        clock.step(86400);
        assertEquals("Clock { [ 00 : 00 : 00 ]     02 / 04 / 2020  }", clock.toString());
    }

    //FIXME : why this doesn't work?
    @Test
    //@Disabled("TO BE FIXED")
    @DisplayName("StepSizeYear")
    void StepSizeYear() {
        Clock clock = new Clock().setLaunchDay();
        clock.step(3.1536E7);
        assertEquals("Clock { [ 00 : 00 : 00 ]     01 / 04 / 2021  }", clock.toString());
    }

    @Test
    @DisplayName("StepSizeMonth")
    void ContinuouslyUpdateMonth() {
        Clock c = new Clock().setLaunchDay();
        int day = 60 * 60 * 24;
        double totaldays = 0;
        int april = 3, year = 0;
        for (int i = april; i < c.getDaysInMonths().length; i++) {
            int days = day * c.getDaysInMonths()[i];
            c.step(days);
            totaldays += days;
            int m = (i < 11 ? i + 2 : i - 10); //current month
            year = m == 1 ? 1 : year;  //january, new year
            assertEquals("Clock { [ 00 : 00 : 00 ]     01 / " + m / 10 + m % 10 + " / 202" + year + "  }", c.toString());
        }
        for (int i = 0; i < april; i++) {
            int days = day * c.getDaysInMonths()[i];
            c.step(days);
            totaldays += days;
            int m = i + 2;
            assertEquals("Clock { [ 00 : 00 : 00 ]     01 / " + m / 10 + m % 10 + " / 2021  }", c.toString());
        }
        assertEquals(3.1536E7, totaldays);

    }

    @Test
    @DisplayName("StepSizeDay")
    void ContinuouslyUpdate() {
        Clock clock = new Clock().setLaunchDay();

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
                //System.out.println("taking step : " + allMonths[i]);
                clock.step(allMonths[i]);  //step 1 month
                assertEquals(1, clock.getDays());
                assertEquals(fromApril[i], clock.getMonths());
            }
    }

    @Test
    @DisplayName("GetDates")
    void testDateEquals() {
        Clock clock = new Clock().setLaunchDay();
        assertEquals(1, clock.getDate().days);
        assertEquals(4, clock.getDate().months);
        assertEquals(2020, clock.getDate().years);
        assertEquals(2020, clock.getYears());
        assertEquals(0, clock.getMin());
        assertEquals(0, clock.getHour());
        assertEquals(0, clock.getSec());
    }

    @ParameterizedTest(name = "testing {0} steps")
    @ValueSource(ints = {10, 20, 60, 120, 240, 1200, 2400})
    void testDifferentStepsIfGetsTheCorrectDay(int stepsToTake) {
        Clock c = new Clock().setLaunchDay();
        String inTwoYears = "Clock { [ 00 : 00 : 00 ]     01 / 06 / 2020  }";
        double twoYearsStep = 60 * 60 * 24 * 61; //sec*min*hours = day
        double singleStepAmountOfSec = twoYearsStep / stepsToTake;
        double diff = twoYearsStep % stepsToTake;


        Assumptions.assumeTrue(singleStepAmountOfSec == (int) singleStepAmountOfSec);
        //no fractions of seconds handling

        for (int i = 0; i < stepsToTake; i++) {
            c.step(singleStepAmountOfSec);
        }
        if (diff != 0)
            c.step(diff);
        assertEquals(inTwoYears, c.toString());

    }


}