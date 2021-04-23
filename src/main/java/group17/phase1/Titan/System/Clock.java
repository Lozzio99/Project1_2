package group17.phase1.Titan.System;

public class Clock {


    private int sec, min, hour, days, months, years;
    private boolean leap;
    private int[] daysInMonths;


    public Clock() {
    }

    public static void main(String[] args) {


        /*
        while (true) {
            c.step(30 * 60);
            System.out.println(c);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
    }

    public Clock setLaunchDay() {
        this.sec = 0;
        this.min = 0;
        this.hour = 0;
        this.days = 1;
        this.months = 4;
        this.years = 2020;
        checkLeap();
        return this;
    }

    public void step(double secStep) {
        int step = (int) secStep;
        secStep(step);
        checkLeap();
    }

    private void secStep(int secStep) {
        int minutes = secStep / 60;
        this.sec += secStep % 60;
        if (this.sec >= 60) {
            minutes += this.sec / 60;
            this.sec = this.sec % 60;
        }
        minStep(minutes);
    }

    private void minStep(int minutes) {

        int hours = minutes / 60;
        this.min += minutes % 60;
        if (this.min >= 60) {
            hours += this.min / 60;
            this.min = this.min % 60;
        }
        hoursStep(hours);
    }

    private void hoursStep(int hours) {
        int days = hours / 24;
        this.hour += hours % 24;
        if (this.hour >= 24) {
            days += this.hour / 24;
            this.hour = this.hour % 24;
        }
        daysStep(days);
    }

    private void daysStep(int days) {
        int months = days / this.daysInMonths[this.months - 1];

        //FIXME : problem here i guess,
        //TODO : receive 365, return days == days ( if ! leap )
        if (days >= 365)
            this.days += days % 365 % this.daysInMonths[this.months - 1];
        else
            this.days += days % this.daysInMonths[this.months - 1];

        if (this.days > this.daysInMonths[this.months - 1]) {
            months += this.days / this.daysInMonths[this.months - 1];
            this.days = (this.days % this.daysInMonths[this.months - 1]);
            this.days = this.days == 0 ? 1 : this.days;
        }
        monthsStep(months);
    }

    private void monthsStep(int months) {
        int years = months / 12;
        this.months += months % 12;
        if (this.months > 12) {
            years += this.months / 12;
            this.months = (this.months % 12);
            this.months = this.months == 0 ? 1 : this.months;
        }
        yearsStep(years);
    }

    private void yearsStep(int years) {
        this.years += years;
    }

    public void checkLeap() {
        leap = this.years % 4 == 0;
        daysInMonths = new int[]{31, (leap ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    }

    @Override
    public String toString() {
        return "Clock { [ " +
                hour / 10 + hour % 10 + " : " +
                min / 10 + min % 10 + " : " +
                sec / 10 + sec % 10 + " ]   °  " +
                days / 10 + days % 10 + " / " +
                months / 10 + months % 10 + " / " +
                this.years + " ° }";
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


}
