package module;

public class Time {
    private static int countWeeks = 1;

    /**
     * Verify that a week didn't pass in between two request.
     * (In order to be sure to execute the weekend queue)
     * @param request
     * @return
     */
    public static boolean checkTime(Request request){
        double timeOfWeek = request.getSendTime()/Time.countWeeks;
        if(timeOfWeek > 10_080)
            return false;
        else
            return true;
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
}
