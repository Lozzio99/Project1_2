package group17.phase1.Titan.Utils;

public class UnitConverter
{
    public static double convert(double needsToBeConverted, unit from_type, unit to_type){
        switch (from_type)
        {
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
    TODO: copy this method and change it to be convertMeters(double meters, to_type) - done
    TODO: copy this method and change it to be convertUA(double UA, to_type) - done
     */

    //umbrella method to convert from kilometres to another measure of distance
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

    //umbrella method to convert from metres to another measure of distance
    public static double convertMeters(double met, unit to_type){
        switch (to_type)
        {
            case Km -> {return convertMetresToKm(met); }
            case UA, LightYear -> {throw new IllegalArgumentException("Unexpected value for conversion");}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }

    //umbrella method to convert from astronomical units to another measure of distance
    public static double convertUA(double UA, unit to_type){
        switch (to_type)
        {
            case Km -> {return convertAUToKM(UA); }
            case meters -> {throw new IllegalArgumentException("Unexpected value for conversion");}
            case LightYear -> {return convertAUToLY(UA); }
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }

    //umbrella method to convert from light years to another measure of distance
    public static double convertLightYears(double LY, unit to_type){
        switch (to_type)
        {
            case Km -> { return convertLYToKM(LY); }
            case UA -> { return convertLYToAu(LY); }
            case meters -> {throw new IllegalArgumentException("Unexpected value for conversion");}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }



    /*
    TODO: implement this 3 methods for each UA and meters changing the conversion type - done
     */

    //methods to convert from kilometres to other distance measurements
    public static double convertKmToMeters(double km){
        return km*1000;
    }
    public static double convertKmToUA(double km){ return km*(float)0.00000000668458712; }
    public static double convertKmToLightYear(double km){ return km*(float)0.0000000000001057; }

    //methods to convert from metres to other distance measurements
    public static double convertMetresToKm(double met) { return met/1000; }

    //methods to convert from AU to other distance measures
    public static double convertAUToKM(double au) {return au*149597871; }
    public static double convertAUToLY(double au) {return au*(float)0.0000158125; }

    //methods to convert from LY to other distance measures
    public static double convertLYToKM(double ly) {return ly*(float)94605284e4; }
    public static double convertLYToAu(double ly) {return ly*63239.7263; }

    //+++++++++++++++ END LENGTH MEASURES ++++++++++++++++//





    //+++++++++++++++ START TIME MEASURES ++++++++++++++++//

    //umbrella method to convert from second to another measure of time
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

    //methods to convert from second to desired measure of time
    public static double convertSecToMillis(double sec){
        return sec*1000;
    }
    public static double convertSecToHour(double sec){
        return sec/3600;
    }
    public static double convertSecToDay(double sec){ return sec/(3600*24); }
    public static double convertSecToYear(double sec){
        return sec/(3600*24*365);
    }





    //umbrella method to convert from millisecond to another measure of time
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

    //methods to convert from millisecond to desired measure of time
    public static double convertMillisToSec(double msec){
        return msec/1000;
    }
    public static double convertMillisToHour(double msec){
        return msec/(1e3*60*60);
    }
    public static double convertMillisToYear(double msec){
        return msec/(1e3*60*60*24*365);
    }
    public static double convertMillisToDay(double msec){
        return msec/(1e3*60*60*24);
    }




    //umbrella method to convert from hour to another measure of time
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

    //methods to convert from hours to desired measure of time
    public static double convertHoursToSec(double sec){
        return sec*3600;
    }
    public static double convertHoursToMillis(double sec){
        return sec*3600000;
    }
    public static double convertHoursToYear(double sec){
        return sec/(24*365);
    }
    public static double convertHoursToDay(double sec){ return sec/24; }



    //methods to convert from year to desired measure of time
    public static double convertYearToHour(double year) {return year*8760; }
    public static double convertYearToDay(double year) {return year*365; }

    //umbrella method to convert from year to another measure of time
    public static double convertYear(double yr, unit to_type){
        switch (to_type)
        {
            case millisecond, second -> { throw new IllegalArgumentException("Unexpected value for conversion");}
            case hour -> { return convertYearToHour(yr); }
            case day -> { return convertYearToDay(yr); }
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }

    //methods to convert from day to desired measure of time
    public static double convertDayToMS(double d) {return d*(24*60*60*1000);}
    public static double convertDayToSec(double d) {return d*(24*60*60);}
    public static double convertDayToHour(double d) {return d*24;}
    public static double convertDayToYear(double d) {return d/365;}

    //umbrella method to convert from day to another measure of time
    public static double convertDay(double day, unit to_type){
        switch (to_type)
        {
            case millisecond->{return convertDayToMS(day);}
            case second->{return convertDayToSec(day);}
            case hour->{return convertDayToHour(day);}
            case year->{return convertDayToYear(day);}
            default -> {assert false : "not valid unit type or unuseful conversion"+ to_type.name();}
        }
        throw new IllegalArgumentException("Unexpected value for conversion");
    }

}
