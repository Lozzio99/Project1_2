package group17.phase1.Titan.System;

import java.util.Calendar;

public class Clock {

    private final Calendar currentDate = Calendar.getInstance();
    public int day, month, year, hour, second, minute;

    public void step(double secondsStep) {
        currentDate.set(year, month - 1, day, hour, minute, second);
        currentDate.add(Calendar.SECOND, (int) (secondsStep));
        day = currentDate.get(Calendar.DAY_OF_MONTH);
        month = currentDate.get(Calendar.MONTH) + 1;
        year = currentDate.get(Calendar.YEAR);
        hour = currentDate.get(Calendar.HOUR);
        second = currentDate.get(Calendar.SECOND);
        minute = currentDate.get(Calendar.MINUTE);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        currentDate.set(Calendar.DAY_OF_MONTH, day);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        currentDate.set(Calendar.MONTH, month - 1);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        currentDate.set(Calendar.YEAR, year);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
        currentDate.set(Calendar.HOUR, hour);
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
        currentDate.set(Calendar.MINUTE, minute);
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
        currentDate.set(Calendar.SECOND, second);
    }

    public void setCurrentDate(int yy, int mm, int dd, int hour, int min, int sec) {
        year = yy;
        month = mm;
        day = dd;
        this.hour = hour;
        minute = min;
        second = sec;
        this.currentDate.set(yy, mm - 1, dd, hour, min, sec);
    }

    @Override
    public String toString() {
        return year + "/" + month + "/" + day + "/" + " " + hour + ":" + minute + ":" + second;
    }

    public Clock getLaunchDay() {
        day = 1;
        month = 4;
        year = 2020;
        hour = 0;
        second = 0;
        minute = 0;
        return this;
    }

}
