package module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.List;


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
     * @param request
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
     * @param request
     * @see Request #refundPayement()
     * @see Huge #processHuge()
     */
    public void processWeekend(Request request){
        request.refundPayment();
        Huge.getInstance().processHuge();
    }

    /**
     * Check if any request finish their job in the time between the last request and the argument.
     * Is so the cores used are free and the request is remove.
     * @see #freeCores(Request)
     * @param lastRequestEmit
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
     * @param request
     * @return
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
     * @param request
     * @exception IllegalFormatException
     */
    public void freeCores(Request request){
        this.coreAmountAvailable += request.getCoresNeeded();
        if(this.coreAmountAvailable > this.getMaxCore())
            throw new IllegalStateException("Problem during the freeing of the cores, Higher than core amount maximum");
    }

    /**
     * Search and return for the next request to finish its job.
     * @return
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
     * @param request
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
     */
    public void iterateOutput(Request request){
        totalMoneyCollected += request.getProcessingTime() * getMachineCostHour();
        totalProcessingTime += request.getProcessingTime();
        totalWaitTime += request.getWaitTime();
        turnaroundSum += request.calcTurnaround();
    }

    /*********************
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

    public void setNumberRequestTreat(int numberRequestTreat) {
        this.numberRequestTreat = numberRequestTreat;
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

    public void setTotalMoneyCollected(double totalMoneyCollected) {
        this.totalMoneyCollected = totalMoneyCollected;
    }

    public double getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(double totalProcessingTime) {
        this.totalProcessingTime = totalProcessingTime;
    }

    public double getTotalWaitTime() {
        return totalWaitTime;
    }

    public void setTotalWaitTime(double totalWaitTime) {
        this.totalWaitTime = totalWaitTime;
    }

    public double getTurnaroundSum() {
        return turnaroundSum;
    }

    public void setTurnaroundSum(double turnaroundSum) {
        this.turnaroundSum = turnaroundSum;
    }
}
