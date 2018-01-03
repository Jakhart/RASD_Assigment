package module;

import java.util.ArrayList;

public class Large extends Queue{
    private final static double machineCostHour =0;
    private static Large large_me = null;

    /*
    Constructors in a singleton pattern
     */
    private Large(){
        this.setCoreAmountAvailable(1024);
        this.setQueueType("Large");
        this.setRunningRequest(new ArrayList<>());
    }

    public static Large getInstance() {
        if(large_me == null)
            large_me = new Large();
        return large_me;
    }
}
