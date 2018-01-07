package test;

import module.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QueueTest {
    private void reloadArray(){
        ShortQueue.getInstance().setRunningRequest(new ArrayList<>());
        Medium.getInstance().setRunningRequest(new ArrayList<>());
        Large.getInstance().setRunningRequest(new ArrayList<>());
        Huge.getInstance().setRunningRequest(new ArrayList<>());
    }

    @BeforeClass
    public static void setUpClass() throws Exception{
        Queue[] queues = {ShortQueue.getInstance(),Medium.getInstance(),Large.getInstance(),Huge.getInstance()};
        for (Queue queue : queues) {
            queue.setRunningRequest(new ArrayList<>());
            queue.setCoreAmountAvailable(queue.getMaxCore());
        }
        Huge.getInstance().setWaitingRequest(new ArrayList<>());
        Huge.getInstance().setWeekendCount(new ArrayList<>());
        Time.setCountWeeks(1);
    }

    /**
     * Test the method processRequest(Request) Oof the Queue class.
     */
    @Test
    public void testProcessRequest(){
        reloadArray();
        Request request1 = new Request(200,0,120,Large.getInstance(),500);
        Request request2 = new Request(250,0,250,Large.getInstance(),524);
        Request request3 = new Request(300,0,120,Large.getInstance(),500);
        Request request4 = new Request(800,0,120,Large.getInstance(),500);
        Request request5 = new Request(6241,0,120,Large.getInstance(),500);
        request5.setUser(new Student(50));
        Queue queue = Large.getInstance();
        queue.processRequest(request1);
        assertEquals(0, request1.getWaitTime(), 0.00001);
        assertEquals(1, Large.getInstance().getRunningRequest().size());
        queue.processRequest(request2);
        assertEquals(0, request2.getWaitTime(), 0.00001);
        assertEquals(2, Large.getInstance().getRunningRequest().size());
        queue.processRequest(request3);
        assertEquals(20, request3.getWaitTime(), 0.00001);
        assertEquals(2, Large.getInstance().getRunningRequest().size());
        queue.processRequest(request4);
        assertEquals(0, request4.getWaitTime(), 0.00001);
        assertEquals(1, Large.getInstance().getRunningRequest().size());
        queue.processRequest(request5);
        assertEquals(50 + request5.getPriceOfRequest(), request5.getUser().getBudget(),0.0001);
    }

    /**
     * Test the method checkCores(Request) of the class Queue.
     */
    @Test
    public void testCheckCores() {
        reloadArray();
        Request request = new Request(new Student(30));
        request.setCoresNeeded(request.getQueueType().getCoreAmountAvailable());
        assertFalse(request.getQueueType().checkCores(request));

        request.setCoresNeeded(request.getQueueType().getCoreAmountAvailable() + 1);
        assertTrue(request.getQueueType().checkCores(request));
    }

    /**
     * Test the method freeCores(Request) of the Queue class.
     */
    @Test
    public void testFreeCores(){
        reloadArray();
        Request request = new Request(Medium.getInstance(), 100);
        Medium.getInstance().setCoreAmountAvailable(105);
        request.getQueueType().freeCores(request);
        assertEquals(205, request.getQueueType().getCoreAmountAvailable());
        try {
            request.getQueueType().freeCores(request);
            fail("Suppose to throw an Illegal State exception");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Test the method nextFinishRequest() of the Queue class.
     */
    @Test
    public void testNextFinishRequest(){
        reloadArray();
        Queue queue = Medium.getInstance();
        Request request = new Request(200, 40, 60, Medium.getInstance(), 10);
        Request request2 = new Request(100, 40, 60, Medium.getInstance(), 10);
        Request request3 = new Request(400, 40, 60, Medium.getInstance(), 10);
        queue.getRunningRequest().add(request);
        queue.getRunningRequest().add(request2);
        queue.getRunningRequest().add(request3);
        assertEquals(100, queue.nextFinishRequest().getSendTime(), 0.001);
    }

    /**
     * Test of the method checkForFinishRequest(Request) of the Queue class.
     */
    @Test
    public void testCheckForFinishRequest(){
        reloadArray();
        Request request1 = new Request(200,0,120,Medium.getInstance(), 10);
        Request request2 = new Request(100,0,120,Medium.getInstance(), 10);
        Request request3 = new Request(300,0,120,Medium.getInstance(), 10);
        Request request4 = new Request(120,0,120,Medium.getInstance(), 10);

        Medium.getInstance().getRunningRequest().add(request1);
        Medium.getInstance().getRunningRequest().add(request2);
        Medium.getInstance().getRunningRequest().add(request3);
        Medium.getInstance().setCoreAmountAvailable(100);
        Medium.getInstance().checkForFinishRequest(request4);
        assertEquals(3, Medium.getInstance().getRunningRequest().size());

        request4.setSendTime(320);
        Medium.getInstance().checkForFinishRequest(request4);
        double min = 1000;
        for (Request request : Medium.getInstance().getRunningRequest()){
            if(min > request.getSendTime() + request.getProcessingTime()){
                min = request.getSendTime() + request.getProcessingTime();
            }
        }
        assertEquals(420, min, 0.0001);
    }

    /**
     * Test of the method processWaitTine() of the Queue class.
     */
    @Test
    public void testProcessWaitTime(){
        reloadArray();
        Request request1 = new Request(200,0,60,Large.getInstance(),32);
        Request request2 = new Request(220,0,30,Large.getInstance(),15);
        Request request3 = new Request(260,0,30,Large.getInstance(),17);
        Queue queue = ShortQueue.getInstance();

        queue.processWaitTime(request1);
        assertEquals(0, request1.getWaitTime(), 0.00001);
        queue.getRunningRequest().add(request1);
        queue.setCoreAmountAvailable(0);
        queue.processWaitTime(request2);
        assertEquals(40, request2.getWaitTime(), 0.0001);
        assertEquals(32, queue.getCoreAmountAvailable(), 0.0001);
        queue.processWaitTime(request3);
        assertEquals(0, request3.getWaitTime(), 0.0001);
    }
}