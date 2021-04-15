package group17.phase1.Titan.Physics;

public class Clock
{
    public static int dd = 1,
            mm = 4,
            yy = 2020,
            hour = 0,
            sec = 0,
            min = 0;

    public static void step(double secondsStep) {
        sec+= secondsStep;
         min = sec/60 ;
         hour = min/60;

         if (mm == 4 || mm == 6 || mm == 9 || mm == 11)
         {
             if (dd >30)
             {
                 dd = 0;
                 mm++;
             }
         }
         else if (mm == 2)
         {
             if (dd>28)
             {
                 dd = 0;
                 mm++;
             }
         }
         else
         {
             if (dd>31)
             {
                 dd = 1;
                 mm++;
             }
         }


    }


    public static int getDay() {
        return dd;
    }

    public static void setDay(int dd) {
        Clock.dd = dd;
    }

    public static int getMinutes() {
        return mm;
    }

    public static void setMinutes(int mm) {
        Clock.mm = mm;
    }

    public static int getYear() {
        return yy;
    }

    public static void setYy(int yy) {
        Clock.yy = yy;
    }

    public static int getHour() {
        return hour;
    }

    public static void setHour(int hour) {
        Clock.hour = hour;
    }

    public static int getSec() {
        return sec;
    }

    public static void setSec(int sec) {
        Clock.sec = sec;
    }

    public static int getMin() {
        return min;
    }

    public static void setMin(int min) {
        Clock.min = min;
    }
}
