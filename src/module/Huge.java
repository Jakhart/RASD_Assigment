package module;

import java.util.ArrayList;

public class Huge extends Queue{
    private final static double machineCostHour =0;
    private final static double maximumRequestTime = 10_080; //The maximum Request time is one week long
    private static ArrayList<Request> waitingRequest;
    private static Huge huge_me = null;

    /*
    Constructors in a singleton pattern
     */
    private Huge(){
        this.setMaxCore(2048);
        this.setCoreAmountAvailable(this.getMaxCore());
        this.setQueueType("Huge");
        this.setRunningRequest(new ArrayList<>());
    }

    public static Huge getInstance() {
        if(huge_me == null)
            huge_me = new Huge();
        return huge_me;
    }

    @Override
    public void processRequest(Request request){
        waitingRequest.add(request);
    }

    public void processWeekend(Request lastRequest){
        if(lastRequest.getQueueType() != huge_me){
            lastRequest.refundPayment();
        }
        else{
            processRequest(lastRequest);
        }
        while(huge_me.getRunningRequest().size() != 0){
            for (Request request : huge_me.getRunningRequest()) {
                while (checkCores(request)) {
                    Request finishingRequest = nextFinishRequest();
                    freeCores(finishingRequest);
                    request.setWaitTime(finishingRequest.getSendTime() + finishingRequest.getProcessingTime()
                            + finishingRequest.getWaitTime() - request.getSendTime());
                    getRunningRequest().remove(finishingRequest);
                }
                if (request.finishDuringWE()) {
                    getRunningRequest().add(request);
                    setCoreAmountAvailable(getCoreAmountAvailable() - request.getCoresNeeded());
                    iterateOutput(request);
                } else {
                    getTotalProcessingTime();
                    request.setProcessingTime(1);
                }
            }
        }
    }

    public double getMachineCostHour() {
        return machineCostHour;
    }
    public double getMaximumRequestTime() {return maximumRequestTime;}
}
