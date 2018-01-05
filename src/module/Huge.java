package module;

import java.util.ArrayList;

public class Huge extends Queue{
    private final static double machineCostHour =70;
    private final static double maximumRequestTime = 10_080; //The maximum Request time is one week long
    private static ArrayList<Request> waitingRequest = new ArrayList<>();
    private static ArrayList<Integer> weekendCount = new ArrayList<>();
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

    /**
     * Process the Huge request queue that is running only during the weekend.
     * @see Huge {@link #processWaitTime(Request)}
     * @see Request#finishDuringWE()
     */
    public void processHuge(){
        Time.setEndWeekend(Time.FULLWEEK * Time.getCountWeeks());
        Time.addCountWeeks();
        for (int i = 0; i < getWaitingRequest().size(); i++) {
            Request request = getWaitingRequest().get(i);
            processWaitTime(request);
            iterateOutput(request);
            //Change the processing time to be able to measure the time past during the weekend.
            request.setProcessingTime(request.getProcessingTime() + Time.getweekendPast());
            setCoreAmountAvailable(getCoreAmountAvailable() - request.getCoresNeeded());
            if (request.finishDuringWE()) {
                getRunningRequest().add(request);
            } else {
                for (int j = i -1 ; j >=0 ; j--) {
                    getWaitingRequest().remove(j);
                    getWeekendCount().remove(j);
                }
                for (int j = 0; j < getWaitingRequest().size(); j++) {
                    getWeekendCount().set(j, getWeekendCount().get(j) + 1);
                }
                request.setProcessingTime(request.getProcessingTime() - Time.WEEKEND);
                getRunningRequest().add(request);
                return;
            }
        }
    }

    /**
     * Process the weekend queue.
     * @param request
     * @see Queue {@link #processHuge()}
     */
    @Override
    public void processWeekend(Request request){
        getRunningRequest().add(request);
        processHuge();
    }

    /**
     * Add the request to the waiting list.
     * @param request
     */
    @Override
    public void processRequest(Request request){
        getWaitingRequest().add(request);
        getWeekendCount().add(0);
    }

    /**
     * Calculate the waitTime and instantiate the time past during the weekend.
     * @param request
     * @see #freeCores(Request)
     * @see #checkCores(Request)
     */
    @Override
    public void processWaitTime(Request request){
        double endWeekendPast = Time.getweekendPast();
        request.setWaitTime(getWeekendCount().get(getWaitingRequest().indexOf(request)) * Time.WEEKEND);
        while (checkCores(request)) {
            Request finishingRequest = nextFinishRequest();
            if(endWeekendPast < Time.getweekendPast() + finishingRequest.getProcessingTime()){
                endWeekendPast = Time.getweekendPast() + finishingRequest.getProcessingTime();
            }
            freeCores(finishingRequest);
            getRunningRequest().remove(finishingRequest);
        }
        request.setWaitTime(request.getWaitTime() + endWeekendPast - Time.getweekendPast());
        Time.setweekendPast(endWeekendPast);
    }

    /**
     * Calculate the wait time of a request if there is one.
     * @return
     */
    @Override
    public Request nextFinishRequest(){
        Request nextFinish = this.getRunningRequest().get(0);
        double minTimeFinish = Time.FULLWEEK;
        for(Request request : this.getRunningRequest()){
            double timeFinish = request.getProcessingTime();
            if (minTimeFinish > timeFinish) {
                minTimeFinish = timeFinish;
                nextFinish = request;
            }
        }
        return nextFinish;
    }

    /****************
    Accessors
     *****************/
    public double getMachineCostHour() {
        return machineCostHour;
    }
    public double getMaximumRequestTime() {return maximumRequestTime;}

    public static ArrayList<Request> getWaitingRequest() {
        return waitingRequest;
    }

    public static ArrayList<Integer> getWeekendCount() {
        return weekendCount;
    }
}
