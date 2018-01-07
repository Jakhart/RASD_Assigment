package module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.List;

/**
 *This class is the super class of all the Queue of the system.
 */
public abstract class Queue {
    private int coreAmountAvailable;
    private int maxCore;
    private int numberRequestTreat = 0;
    private String queueType;
    private List<Request> runningRequest;
    //Value needed for output file
    private double totalMoneyCollected;
    private double totalProcessingTime;
    private double totalWaitTime;
    private double turnaroundSum;

    /**
     *Process the request and iterate the value needed for the input file.
     * @param request - Request object to process.
     * @see #checkForFinishRequest(Request)
     * @see #processWaitTime(Request)
     * @see #iterateOutput(Request)
     * @see Request#refundPayment()
     */
    public void processRequest(Request request){
        checkForFinishRequest(request);
        processWaitTime(request);
        if(request.finishBeforeWE()){
            getRunningRequest().add(request);
            setCoreAmountAvailable(getCoreAmountAvailable() - request.getCoresNeeded());
            iterateOutput(request);
        }else{
            request.refundPayment();
        }
    }

    /**
     * Process the request if their send time is during a weekend.
     * @param request - Request object to process.
     * @see Request #refundPayement()
     * @see Huge #processHuge()
     */
    public void processWeekend(Request request){
        request.refundPayment();
        Huge.getInstance().processHuge(request);
    }

    /**
     * Check if any request finish their job in the time between the last request and the argument.
     * Is so the cores used are free and the request is remove.
     * @see #freeCores(Request)
     * @param lastRequestEmit - Request object that is processing.
     */
    public void checkForFinishRequest(Request lastRequestEmit){
        for (int i = this.getRunningRequest().size() - 1; i >= 0 ; i--) {
            Request request = this.getRunningRequest().get(i);
            double timeFinish = request.getSendTime() + request.getProcessingTime() + request.getWaitTime();
            if(lastRequestEmit.getSendTime() >= timeFinish){
                freeCores(request);
                getRunningRequest().remove(i);
            }
        }
    }

    /**
     * Check if the request can be process by its queue.
     * @param request - Request object that is processing
     * @return - boolean type, true if the queue cannot provided the requested cores.
     */
    public boolean checkCores(Request request){
        if(this.getCoreAmountAvailable() < request.getCoresNeeded()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Free the cores used by a request once the request has ended.
     * @param request - Request object, that just finish its task.
     * @exception IllegalFormatException - Exception rise if the core amount maximum of the queue is exceed.
     */
    public void freeCores(Request request){
        this.coreAmountAvailable += request.getCoresNeeded();
        if(this.coreAmountAvailable > this.getMaxCore())
            throw new IllegalStateException("Problem during the freeing of the cores, Higher than core amount maximum");
    }

    /**
     * Search and return for the next request to finish its job.
     * @return - Request object, the next request to finish it's task.
     */
    public Request nextFinishRequest(){
        Request nextFinish = this.getRunningRequest().get(0);
        double minTimeFinish = Time.FULLWEEK;
        for(Request request : this.getRunningRequest()){
            double timeFinish = request.getSendTime() + request.getProcessingTime() + request.getWaitTime();
            if (minTimeFinish > timeFinish) {
                minTimeFinish = timeFinish;
                nextFinish = request;
            }
        }
        return nextFinish;
    }

    /**
     * Calculate the wait time if there is one.
     * @param request - Request object to process
     * @see #checkCores(Request)
     * @see #nextFinishRequest()
     * @see #freeCores(Request)
     */
    public void processWaitTime(Request request){
        while(checkCores(request)){
            Request finishingRequest = nextFinishRequest();
            freeCores(finishingRequest);
            request.setWaitTime(finishingRequest.getSendTime() + finishingRequest.getProcessingTime()
                    + finishingRequest.getWaitTime() - request.getSendTime());
            getRunningRequest().remove(finishingRequest);
        }
    }

    /**
     * Iterate the value of the output file.
     * @param request The information of this request will added to the general information of the output file.
     */
    public void iterateOutput(Request request){
        totalMoneyCollected += request.getProcessingTime() * getMachineCostHour();
        totalProcessingTime += request.getProcessingTime();
        totalWaitTime += request.getWaitTime();
        turnaroundSum += request.calcTurnaround();
        numberRequestTreat++;
    }

    /*
    Accessors
     ***********************/


    public abstract double getMachineCostHour();

    public abstract double getMaximumRequestTime();

    public int getCoreAmountAvailable() {
        return coreAmountAvailable;
    }

    public void setCoreAmountAvailable(int coreAmountAvailable) {
        this.coreAmountAvailable = coreAmountAvailable;
    }

    public int getMaxCore() {
        return maxCore;
    }

    public void setMaxCore(int maxCore) {
        this.maxCore = maxCore;
    }

    public int getNumberRequestTreat() {
        return numberRequestTreat;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public List<Request> getRunningRequest() {
        return runningRequest;
    }

    public void setRunningRequest(List<Request> runningRequest) {
        this.runningRequest = runningRequest;
    }

    public double getTotalMoneyCollected() {
        return totalMoneyCollected;
    }

    public double getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public double getTotalWaitTime() {
        return totalWaitTime;
    }

    public double getTurnaroundSum() {
        return turnaroundSum;
    }
}
