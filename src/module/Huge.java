package module;

import java.util.ArrayList;

public class Huge extends Queue{
    private final static double machineCostHour =0;
    private static ArrayList<Request> waitingRequest;
    private static Huge huge_me = null;

    /*
    Constructors in a singleton pattern
     */
    private Huge(){
        this.setCoreAmountAvailable(2048);
        this.setQueueType("Huge");
        this.setRunningRequest(new ArrayList<>());
    }

    public static Huge getInstance() {
        if(huge_me == null)
            huge_me = new Huge();
        return huge_me;
    }
}