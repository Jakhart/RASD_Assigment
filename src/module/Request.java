package module;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.Comparator;

public class Request implements Comparator{
    private double sendTime = 0;
    private double waitTime;
    private double processingTime;
    private User user;
    private Queue queueType;
    private int coresNeeded;
    private double priceOfRequest;

    /**
     *
     */
    public Request(Queue queue, int coresNeeded){
        this.queueType = queue;
        this.coresNeeded = coresNeeded;
    }
    /**
     * Global constructor use mainly for testing
     */
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
        this.coresNeeded = (int) Math.round(Random.exponentialProbability(200, user.getParameter()));
        this.queueType = checkSize(this.coresNeeded);
        this.generateProcessingTime();
        this.priceOfRequest = this.getProcessingTime()*this.queueType.getMachineCostHour();
        user.setTimeBetweenTwoRequest(user.getTimeBetweenTwoRequest() +
                Random.exponentialProbability(200, user.getParameter()));
    }

    /**
     * Find the adequate queue for the request, in terms of the number of cores requested.
     * -Warning- This method is based on
     * @param coresNumber - the number of cores asked by the user to treat this request.
     * @return a String with the type of the request (Short, Medium, Large or Huge)
     * @exception IllegalArgumentException
     */
    public static Queue checkSize(int coresNumber){
        if(coresNumber > 0 && coresNumber <= ShortQueue.getInstance().getMaxCore()){
            return ShortQueue.getInstance();
        } else if(coresNumber > ShortQueue.getInstance().getMaxCore()
                && coresNumber <= Medium.getInstance().getCoreAmountAvailable()){
            return Medium.getInstance();
        } else if(coresNumber > Medium.getInstance().getMaxCore()
                && coresNumber <= Large.getInstance().getMaxCore()){
            return Large.getInstance();
        } else if(coresNumber > Large.getInstance().getMaxCore()
                && coresNumber <= Huge.getInstance().getMaxCore()){
            return Huge.getInstance();
        } else{
            throw new IllegalArgumentException("The value of the cores is incorrect: " + coresNumber);
        }
    }

    /**
     * Compare to method from the Interface Comparable - Compare two request by comparing their sendTime.
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Object o1, Object o2) {
        Request r1 = (Request) o1;
        Request r2 = (Request) o2;
        if(r1.getSendTime() > r2.getSendTime())
            return 1;
        else if(r1.getSendTime() == r2.getSendTime())
            return 0;
        else
            return -1;
    }

    /**
     * Generate a randomize processing time depending on the size of the request.
     * @return
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
     * @return
     * @exception ValueException
     */
    public double calcTurnaround(){
        if(this.getProcessingTime() == 0){
            throw new ValueException("The processing time is equal to 0");
        }
        return (this.getWaitTime() + this.getProcessingTime()) / this.getProcessingTime();
    }

    /**
     * Test if the request finish before the cutoff time.
     * @return
     * @exception IllegalArgumentException
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
     * @return
     */
    public boolean finishDuringWE(){
        if(this.getProcessingTime() <= Time.WEEKEND)
            return true;
        else{
            return false;
        }
    }

    /******************************************
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
