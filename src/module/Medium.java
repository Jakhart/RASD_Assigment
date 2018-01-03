package module;

import java.util.ArrayList;

public class Medium extends Queue{
    private final static double machineCostHour = 10;
    private final static double maximumRequestTime = 480;
    private static Medium medium_me = null;

    /*
    Constructors in a singleton pattern
     */
    private Medium(){
        this.setCoreAmountAvailable(205);
        this.setQueueType("Medium");
        this.setRunningRequest(new ArrayList<>());
    }

    public static Medium getInstance() {
        if(medium_me == null)
            medium_me = new Medium();
        return medium_me;
    }

    public double getMachineCostHour() {
        return machineCostHour;
    }

    public double getMaximumRequestTime() {return maximumRequestTime;}

}
