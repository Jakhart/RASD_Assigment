package module;

import java.util.ArrayList;

public class ShortQueue extends Queue {
    private final static double machineCostHour =0;
    private final static double maximumRequestTime = 60;
    private static ShortQueue short_me = null;

    /*
    Constructors in a singleton pattern
     */
    private ShortQueue(){
        this.setCoreAmountAvailable(32);
        this.setQueueType("Short");
        this.setRunningRequest(new ArrayList<>());
    }

    public static ShortQueue getInstance() {
        if(short_me == null)
            short_me = new ShortQueue();
        return short_me;
    }


    /*
    Accessors
     */
    public void setShort_me(ShortQueue short_me) {
        this.short_me = short_me;
    }
    public double getMachineCostHour() {
        return machineCostHour;
    }
    public double getMaximumRequestTime() {return maximumRequestTime;}
}
