package module;

/**
 * This class enable us to follow the time of the simulation thought the sendTime
 * attribute of the request class.
 */
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
     * @param request - the request to check
     * @return - true if a week pass.
     */
    public static boolean checkTime(Request request){
        double timeOfWeek = request.getSendTime()/Time.countWeeks;
        if(timeOfWeek > Time.FULLWEEK)
            return true;
        else
            return false;
    }

    /**
     * Test if a request is send during the weekend or not.
     * @param request - the request to check
     * @return - true if it's the weekend
     */
    public static boolean checkWeekend(Request request){
        if(Math.round(request.getSendTime()- (countWeeks - 1)*Time.FULLWEEK) > CUTOFF){
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

    public static void setCountWeeks(int countWeeks) {
        Time.countWeeks = countWeeks;
    }
}
