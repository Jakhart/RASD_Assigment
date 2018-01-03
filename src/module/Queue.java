package module;

import java.util.ArrayList;
import java.util.List;


public abstract class Queue {
    private static int coreAmountAvailable;
    private static int numberRequestTreat = 0;
    private String queueType;
    private static List<Request> runningRequest;
    //Value needed for output file
    private static double totalMoneyCollected;
    private static double totalProcessingTime;
    private static double totalWaitTime;
    private static double turnaroundSum;




    /*
    Accessors
     */

    public static int getCoreAmountAvailable() {
        return coreAmountAvailable;
    }

    public static void setCoreAmountAvailable(int coreAmountAvailable) {
        Queue.coreAmountAvailable = coreAmountAvailable;
    }

    public static int getNumberRequestTreat() {
        return numberRequestTreat;
    }

    public static void setNumberRequestTreat(int numberRequestTreat) {
        Queue.numberRequestTreat = numberRequestTreat;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public static List<Request> getRunningRequest() {
        return runningRequest;
    }

    public static void setRunningRequest(List<Request> runningRequest) {
        Queue.runningRequest = runningRequest;
    }

    public static double getTotalMoneyCollected() {
        return totalMoneyCollected;
    }

    public static void setTotalMoneyCollected(double totalMoneyCollected) {
        Queue.totalMoneyCollected = totalMoneyCollected;
    }

    public static double getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public static void setTotalProcessingTime(double totalProcessingTime) {
        Queue.totalProcessingTime = totalProcessingTime;
    }

    public static double getTotalWaitTime() {
        return totalWaitTime;
    }

    public static void setTotalWaitTime(double totalWaitTime) {
        Queue.totalWaitTime = totalWaitTime;
    }

    public static double getTurnaroundSum() {
        return turnaroundSum;
    }

    public static void setTurnaroundSum(double turnaroundSum) {
        Queue.turnaroundSum = turnaroundSum;
    }
}
