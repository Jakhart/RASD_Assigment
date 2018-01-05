package module;

public class Time {
    private static int countWeeks = 1;
    public final static double CUTOFF = 6240;
    public final static double FULLWEEK = 10_080;
    public final static double WEEKEND = FULLWEEK - CUTOFF; //3840
    private static double weekendPast = 0;
    private static double endWeekend = 0;

    /**
     * Verify that a week didn't pass in between two request.
     * (In order to be sure to execute the weekend queue)
     * @param request
     * @return
     */
    public static boolean checkTime(Request request){
        double timeOfWeek = request.getSendTime()/Time.countWeeks;
        if(timeOfWeek > 10_080)
            return true;
        else
            return false;
    }

    /**
     * Test if a request is send during the weekend or not.
     * @param request
     * @return
     */
    public static boolean checkWeekend(Request request){
        if(Math.round(request.getSendTime()/countWeeks) > CUTOFF){
            return true;
        }else{
            return false;
        }
    }

    /*
    Accessors
     */
    public static int getCountWeeks() {
        return countWeeks;
    }

    public static void addCountWeeks() {
        Time.countWeeks++;
    }

    public static double getweekendPast() {
        return weekendPast;
    }

    public static void setweekendPast(double weekendPast) {
        Time.weekendPast = weekendPast;
    }

    public static double getEndWeekend() {
        return endWeekend;
    }

    public static void setEndWeekend(double endWeekend) {
        Time.endWeekend = endWeekend;
    }
}
