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
     * Test the method checkForFinishRequest(Request) of the Queue class.
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

    @Test
    public void testProcessRequest(){

    }
}