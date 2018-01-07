package module;

import java.util.ArrayList;

public class Large extends Queue{
    private final static double machineCostHour = 15;
    private final static double maximumRequestTime = 960;
    private static Large large_me = null;

    /*
    Constructors in a singleton pattern
     */
    private Large(){
        this.setMaxCore(1024);
        this.setCoreAmountAvailable(this.getMaxCore());
        this.setQueueType("Large");
        this.setRunningRequest(new ArrayList<>());
    }

    public static Large getInstance() {
        if(large_me == null)
            large_me = new Large();
        return large_me;
    }

    public double getMachineCostHour() {
        return machineCostHour;
    }
    public double getMaximumRequestTime() {return maximumRequestTime;}
}
