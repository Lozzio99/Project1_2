package group17.System;

import static group17.Config.ERROR_EVALUATION;
import static group17.Config.REPORT;
import static group17.Main.simulationInstance;

public class Clock {


    private static final String[] monthStrings =
            {"January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"};
    private int sec, min, hour, days, months, years;
    private boolean leap;
    private int[] daysInMonths;

    public Clock() {
        sec = 0;
        min = 0;
        hour = 0;
        days = 0;
        months = 0;
        years = 0;
    }

    public static void main(String[] args) {
        Clock c = new Clock().setLaunchDay().setInitialTime(00, 50, 23);
        int k = 0;
        while (true) {

            c.step(60);
            if (k % 1000 == 0)
                System.out.println(c);
            Thread.onSpinWait();
            k++;
        }

    }

    public Clock setLaunchDay() {
        setInitialDay(1, 4, 2020);
        setInitialTime(0, 0, 0);
        checkLeap();
        if (ERROR_EVALUATION) checkFirst();
        return this;
    }

    public Clock setInitialDay(int days, int month, int years) {
        this.days = days;
        this.months = month;
        this.years = years;
        return this;
    }

    public Clock setInitialTime(int sec, int min, int hours) {
        this.sec = sec;
        this.min = min;
        this.hour = hours;
        return this;
    }

    public synchronized boolean step(double secStep) {
        int step = (int) secStep;
        if (step != 0)
            secStep(step);
        checkLeap();
        if (!ERROR_EVALUATION) return false;
        return checkFirst();
    }

    private boolean checkFirst() {
        if (this.days == 1 && this.hour == 0 && this.min == 0 && this.sec == 0) {
            if (REPORT) simulationInstance.getReporter().report("FIRST OF MONTH - EVALUATING ERRORS");
            return true;
        }
        return false;
    }

    private synchronized void secStep(int secStep) {
        int minutes = secStep / 60;
        this.sec += secStep % 60;
        if (this.sec >= 60) {
            minutes += this.sec / 60;
            this.sec = this.sec % 60;
        }
        if (minutes != 0)
            minStep(minutes);
    }

    private synchronized void minStep(int minutes) {
        int hours = minutes / 60;
        this.min += minutes % 60;
        if (this.min >= 60) {
            hours += this.min / 60;
            this.min = this.min % 60;
        }
        if (hours != 0)
            hoursStep(hours);
    }

    private synchronized void hoursStep(int hours) {
        int days = hours / 24;
        this.hour += hours % 24;
        if (this.hour >= 24) {
            days += this.hour / 24;
            this.hour = this.hour % 24;
        }
        if (days != 0)
            daysStep(days);
    }

    private synchronized void daysStep(int days) {
        int months = days / this.daysInMonths[this.months - 1];

        //FIXME : problem here i guess,
        //TODO : receive 365, return days == days ( if ! leap )
        if (days >= 365)
            this.days += (days % 365) % this.daysInMonths[this.months - 1];
        else
            this.days += days % this.daysInMonths[this.months - 1];


        if (this.days > this.daysInMonths[this.months - 1]) {
            months += this.days / this.daysInMonths[this.months - 1];
            this.days = (this.days % this.daysInMonths[this.months - 1]);
            this.days = this.days == 0 ? 1 : this.days;
        }
        if (months != 0)
            monthsStep(months);
    }

    private synchronized void monthsStep(int months) {
        int years = months / 12;
        this.months += months % 12;
        if (this.months > 12) {
            years += this.months / 12;
            this.months = (this.months % 12);
            this.months = this.months == 0 ? 1 : this.months;
        }
        yearsStep(years);
    }

    private synchronized void yearsStep(int years) {
        this.years += years;
    }

    public void checkLeap() {
        leap = this.years % 4 == 0;
        daysInMonths = new int[]{31, (leap ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    }

    @Override
    public synchronized String toString() {
        return "Clock { [ " +
                hour / 10 + hour % 10 + " : " +
                min / 10 + min % 10 + " : " +
                sec / 10 + sec % 10 + " ]     " +
                days / 10 + days % 10 + " / " +
                months / 10 + months % 10 + " / " +
                this.years + "  }";
    }

    public int getSec() {
        return sec;
    }

    public int getMin() {
        return min;
    }

    public int getHour() {
        return hour;
    }

    public int getDays() {
        return days;
    }

    public int getMonths() {
        return months;
    }

    public int getYears() {
        return years;
    }

    public int[] getDaysInMonths() {
        return daysInMonths;
    }

    public String monthString() {
        return monthStrings[months - 1];
    }

    public final Date getDate() {
        final Date d = new Date();
        d.sec = this.sec;
        d.min = this.min;
        d.hours = this.hour;
        d.days = this.days;
        d.months = this.months;
        d.years = this.years;
        return d;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Integer.hashCode(sec);
        hash = 31 * hash + Integer.hashCode(min);
        hash = 31 * hash + Integer.hashCode(hour);
        hash = 31 * hash + Integer.hashCode(days);
        hash = 31 * hash + Integer.hashCode(months);
        hash = 31 * hash + Integer.hashCode(years);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Clock) {
            Clock o = (Clock) obj;
            return sec == o.sec && min == o.min &&
                    hour == o.hour && days == o.days &&
                    months == o.months && years == o.years;
        }
        return false;
    }

}
