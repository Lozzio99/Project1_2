package group17.phase1.Titan.Utils;

public class UnitConverter
{
    public static double convert(double needsToBeConverted, unit from_type, unit to_type){
        switch (from_type){
            case year -> {return convertYear(needsToBeConverted,to_type);}
            case day -> {return convertDay(needsToBeConverted,to_type);}
            case hour -> {return convertHour(needsToBeConverted,to_type);}
            case second -> {return convertSeconds(needsToBeConverted,to_type);}
            case millisecond -> {return convertMillis(needsToBeConverted,to_type);}


            case meters -> {return convertMeters(needsToBeConverted,to_type);}
            case Km -> {return convertKm(needsToBeConverted,to_type);}
            case UA -> {return convertUA(needsToBeConverted,to_type);}
            case LightYear -> {return convertLightYears(needsToBeConverted,to_type);}

            default -> {assert false : "wtf?";}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }

    /*
    TODO: copy this method and change it to be convertMeters(double meters, to_type)
    TODO: copy this method and change it to be convertUA(double UA, to_type)
     */
    public static double convertKm(double km,unit to_type){
        switch (to_type)
        {
            case meters -> {return convertKmToMeters(km);}
            case UA -> {return convertKmToUA(km);}
            case LightYear -> {return convertKmToLightYear(km);}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }

    //implement those methods below as in the above style
    public static double convertMeters(double met, unit to_type){
        switch (to_type)
        {
            case Km -> {}
            case UA -> {}
            case LightYear -> {}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }

    public static double convertUA(double UA, unit to_type){
        switch (to_type)
        {
            case Km -> {}
            case meters -> {}
            case LightYear -> {}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }

    public static double convertLightYears(double LY, unit to_type){
        switch (to_type)
        {
            case Km -> {}
            case UA -> {}
            case meters -> {}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }



    /*
    TODO: implement this 3 methods for each UA and meters changing the conversion type
     */
    public static double convertKmToMeters(double km){
        return km/1000;
    }
    public static double convertKmToUA(double km){
        return 0;
    }
    public static double convertKmToLightYear(double km){
        return 0;
    }

    //+++++++++++++++ END LENGTH MEASURES ++++++++++++++++//



    //+++++++++++++++ START TIME MEASURES ++++++++++++++++//


    public static double convertSeconds(double seconds, unit to_type){
        switch (to_type){
            case millisecond ->{ return convertSecToMillis(seconds);}
            case hour -> {return convertSecToHour(seconds);}
            case day -> {return convertSecToDay(seconds);}
            case year -> {return convertSecToYear(seconds);}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }
    public static double convertSecToMillis(double sec){
        return sec*1000;
    }
    public static double convertSecToHour(double sec){
        return sec/60;
    }
    public static double convertSecToDay(double sec){
        return sec/60;
    }
    public static double convertSecToYear(double sec){
        return sec/60;
    }






    public static double convertMillis(double millis, unit to_type){
        switch (to_type){
            case second ->{ return convertMillisToSec(millis);}
            case hour -> {return convertMillisToHour(millis);}
            case day -> {return convertMillisToDay(millis);}
            case year -> {return convertMillisToYear(millis);}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }
    public static double convertMillisToSec(double sec){
        return sec*1000;
    }
    public static double convertMillisToHour(double sec){
        return sec/60;
    }
    public static double convertMillisToYear(double sec){
        return sec/60;
    }
    public static double convertMillisToDay(double sec){
        return sec/60;
    }





    public static double convertHour(double hr, unit to_type){
        switch (to_type){
            case millisecond ->{ return convertHoursToMillis(hr);}
            case second -> {return convertHoursToSec(hr);}
            case day -> {return convertHoursToDay(hr);}
            case year -> {return convertHoursToYear(hr);}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }
    public static double convertHoursToSec(double sec){
        return sec*1000;
    }
    public static double convertHoursToMillis(double sec){
        return sec/60;
    }
    public static double convertHoursToYear(double sec){
        return sec/60;
    }
    public static double convertHoursToDay(double sec){
        return sec/60;
    }




    public static double convertYear(double yr, unit to_type){
        switch (to_type)
        {
            case millisecond->{}
            case second->{}
            case hour->{}
            case day->{}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }




    public static double convertDay(double day, unit to_type){
        switch (to_type)
        {
            case millisecond->{}
            case second->{}
            case hour->{}
            case year->{}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }

}
