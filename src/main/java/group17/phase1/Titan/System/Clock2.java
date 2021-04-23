package group17.phase1.Titan.System;

public class Clock2 {


    private int sec, min, hour, days, months, years;
    private boolean leap;
    private int[] daysInMonths;


    public Clock2() {
    }

    public static void main(String[] args) {
        Clock2 c = new Clock2().setLaunchDay();
        while (true) {
            c.step(60 * 60 * 24);
            System.out.println(c);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Clock2 setLaunchDay() {
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
        int months = days / this.daysInMonths[this.months];
        this.days += days % this.daysInMonths[this.months];
        if (this.days >= this.daysInMonths[this.months]) {
            months += this.days / this.daysInMonths[this.months];
            this.days = (this.days % this.daysInMonths[this.months]) + 1;
        }
        monthsStep(months);
    }

    private void monthsStep(int months) {
        int years = months / 12;
        this.months += months % 12;
        if (this.months >= 12) {
            years += this.months / 12;
            this.months = (this.months % 12) + 1;
        }
        yearsStep(years);
    }

    private void yearsStep(int years) {
        this.years += years;
        checkLeap();
    }

    public void checkLeap() {
        if (this.years % 4 == 0) {
            leap = true;
        }
        daysInMonths = new int[]{31, leap ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    }

    @Override
    public String toString() {
        return "Clock2{ [" +
                this.hour + " : " + this.min + " : " + this.sec + " ]" +
                this.days + " / " + this.months + " / " + this.years +
                '}';
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

}
