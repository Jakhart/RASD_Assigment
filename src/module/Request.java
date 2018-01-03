package module;

import java.util.Comparator;

public class Request implements Comparator{
    //private int requestNumber;
    private int coresNeeded;
    private double processingTime;
    private double sendTime = 0;
    private double waitTime;
    private User user;
    private String size;

    /**
     * Constructor
     * @param user - The user that create the request
     */
    public Request(User user){
        this.sendTime = user.getTimeBetweenTwoRequest();
        this.coresNeeded = (int) Math.round(Random.exponentialProbability(200, user.getParameter()));
        //this.size = checkSize(this.coresNeeded);
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
        else if(sendTime == r2.getSendTime())
            return 0;
        else
            return -1;
    }

    /*
     ACCESSORS
     */
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
