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


}
