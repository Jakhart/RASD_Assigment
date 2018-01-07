package module;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.Comparator;

/**
 * Request class that reprenst the request.
 */
public class Request implements Comparator<Request>{
    private double sendTime = 0;
    private double waitTime;
    private double processingTime;
    private User user;
    private Queue queueType;
    private int coresNeeded;
    private double priceOfRequest;


    public Request(Queue queue, int coresNeeded){
        this.queueType = queue;
        this.coresNeeded = coresNeeded;
    }

    public Request(double sendTime, double waitTime, double processingTime, Queue queue, int coresNeeded){
        this.sendTime = sendTime;
        this.waitTime = waitTime;
        this.processingTime = processingTime;
        this.queueType = queue;
        this.coresNeeded = coresNeeded;
        this.priceOfRequest = queue.getMachineCostHour() * processingTime;
    }
    /**
     * Constructor Random
     * @param user - The user that create the request
     */
    public Request(User user){
        this.user = user;
        this.sendTime = user.getTimeBetweenTwoRequest();
        this.coresNeeded = (int) Math.round(Simulation.exponentialProbability(2048, user.getParameter()));
        this.queueType = checkSize();
        this.generateProcessingTime();
        this.priceOfRequest = this.getProcessingTime()*this.queueType.getMachineCostHour();
        user.setTimeBetweenTwoRequest(user.getTimeBetweenTwoRequest() +
                Simulation.exponentialProbability(10000, user.getParameter()));
    }

    /**
     * Find the adequate queue for the request, in terms of the number of cores requested.
     * -Warning- This method is based on
     * @return The instance queue associated to this request
     * @exception IllegalArgumentException - Appear if the cores Amount requested is impossible to compute.
     */
    public Queue checkSize(){
        if(getCoresNeeded() > 0 && getCoresNeeded() <= ShortQueue.getInstance().getMaxCore()){
            return ShortQueue.getInstance();
        } else if(getCoresNeeded() > ShortQueue.getInstance().getMaxCore()
                && getCoresNeeded() <= Medium.getInstance().getMaxCore()){
            return Medium.getInstance();
        } else if(getCoresNeeded() > Medium.getInstance().getMaxCore()
                && getCoresNeeded() <= Large.getInstance().getMaxCore()){
            return Large.getInstance();
        } else if(getCoresNeeded() > Large.getInstance().getMaxCore()
                && getCoresNeeded() <= Huge.getInstance().getMaxCore()){
            return Huge.getInstance();
        } else{
            throw new IllegalArgumentException("The value of the cores is incorrect: " + getCoresNeeded());
        }
    }

    /**
     * Compare to method from the Interface Comparable - Compare two request by comparing their sendTime.
     * @param o1 - The first request to compare
     * @param o2 - The second request to compare
     * @return - An int depending on the comparison o1 bigger then o2 will be 1, the inverse -1 and 0 if they are equal.
     */
    @Override
    public int compare(Request o1, Request o2) {
        Request r1 =  o1;
        Request r2 =  o2;
        if(r1.getSendTime() > r2.getSendTime())
            return 1;
        else if(r1.getSendTime() == r2.getSendTime())
            return 0;
        else
            return -1;
    }

    /**
     * Generate a randomize processing time depending on the size of the request.
     */
    public void generateProcessingTime(){
        this.processingTime = Math.random() * this.queueType.getMaximumRequestTime() + 1;
    }

    /**
     * Refund the payment of the request in the case,
     * where it was send during a cutoff hour or the weekend
     */
    public void refundPayment(){
        this.getUser().setBudget(this.getUser().getBudget() + this.getPriceOfRequest());
    }

    /**
     * Calculate the turnaround value for the instance request.
     * @return - The turnaround value of this request.
     * @exception ValueException - Exception raise when the processing time is equal to zero.
     */
    public double calcTurnaround(){
        if(this.getProcessingTime() == 0){
            throw new ValueException("The processing time is equal to 0");
        }
        return (this.getWaitTime() + this.getProcessingTime()) / this.getProcessingTime();
    }

    /**
     * Test if the request finish before the cutoff time.
     * @return - a boolean, true means that the request finis before weekend.
     * @exception IllegalArgumentException - Exception raise if the time is incorrect.
     */
    public boolean finishBeforeWE(){
        double finishingTime = (this.getSendTime() + this.getProcessingTime() + this.getWaitTime())
                - (Time.getCountWeeks() - 1)*Time.FULLWEEK;
        if(finishingTime > Time.FULLWEEK || finishingTime < 0)
            throw new IllegalArgumentException("The finishing time of the request is invalid: " + finishingTime);
        if(finishingTime > Time.CUTOFF) {
            return false;
        }else{
            return true;
        }
    }

    /**
     * Test if a request finish before the end of the end of the weekend (Monday 9:00 AM)
     * Only useful for the huge request.
     * @return - a boolean, true mean that the request finish during the weekend.
     */
    public boolean finishDuringWE(){
        return this.getProcessingTime() <= Time.WEEKEND;
    }

    /*
     ACCESSORS
     ******************************************/

    public int getCoresNeeded() {
        return coresNeeded;
    }

    public void setCoresNeeded(int coresNeeded) {
        this.coresNeeded = coresNeeded;
    }

    public double getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(double processingTime) {
        this.processingTime = processingTime;
    }

    public double getSendTime() {
        return sendTime;
    }

    public void setSendTime(double sendTime) {
        this.sendTime = sendTime;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Queue getQueueType() {
        return queueType;
    }

    public void setQueueType(Queue queueType) {
        this.queueType = queueType;
    }

    public double getPriceOfRequest() {
        return priceOfRequest;
    }

    public void setPriceOfRequest(double priceOfRequest) {
        this.priceOfRequest = priceOfRequest;
    }
}
