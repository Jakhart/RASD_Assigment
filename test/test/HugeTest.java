package test;

import module.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HugeTest {
    private void reloadArray(){
        ShortQueue.getInstance().setRunningRequest(new ArrayList<>());
        Medium.getInstance().setRunningRequest(new ArrayList<>());
        Large.getInstance().setRunningRequest(new ArrayList<>());
        Huge.getInstance().setRunningRequest(new ArrayList<>());;
        Huge.getInstance().setWaitingRequest(new ArrayList<>());
        Huge.getInstance().setCoreAmountAvailable(Huge.getInstance().getMaxCore());
        Huge.getInstance().setWeekendCount(new ArrayList<>());
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
     * Test of processHuge of the Huge class.
     */
    @Test
    public void testProcessHuge(){
        reloadArray();
        Request request1 = new Request(300,0,1000, Large.getInstance(),2048);
        Request request2 = new Request(6500,0,1000,Large.getInstance(),500);
        Request request3 = new Request(700,0,2840,Large.getInstance(),500);
        Request request4 = new Request(6590,0,2840,Large.getInstance(),2048);
        Huge queue = Huge.getInstance();
        queue.processRequest(request1);
        queue.processRequest(request2);
        queue.processRequest(request3);
        queue.processRequest(request4);
        queue.processHuge(request4);

        assertEquals(1000, request2.getWaitTime(), 0.0001);
        assertEquals(1, queue.getRunningRequest().size());
        assertEquals(2, Time.getCountWeeks());
        assertEquals(1, Huge.getInstance().getWeekendCount().get(0), 0.00001);
    }

    /**
     * Test of processWaitTime of the Huge class.
     */
    @Test
    public void testProcessWaitTime(){
        reloadArray();
        Request request1 = new Request(200,0,1000,Huge.getInstance(),2048);
        Request request2 = new Request(220,0,1000,Huge.getInstance(),1000);
        Request request3 = new Request(260,0,1500,Huge.getInstance(),1000);
        Request request4 = new Request(260,0,3840,Huge.getInstance(),2048);

        Queue queue = Huge.getInstance();
        queue.processRequest(request1);
        queue.processRequest(request2);
        queue.processRequest(request3);
        queue.processRequest(request4);
        queue.processWaitTime(request1);
        queue.getRunningRequest().add(request1);
        queue.setCoreAmountAvailable(0);
        assertEquals(0, request1.getWaitTime(), 0.00001);
        queue.processWaitTime(request2);
        queue.getRunningRequest().add(request2);
        queue.setCoreAmountAvailable(queue.getCoreAmountAvailable() - 1000);
        assertEquals(1000, request2.getWaitTime(), 0.00001);
        queue.processWaitTime(request3);
        queue.getRunningRequest().add(request3);
        queue.setCoreAmountAvailable(queue.getCoreAmountAvailable() - 1000);
        assertEquals(0, request3.getWaitTime(), 0.00001);
        queue.processWaitTime(request4);
        queue.getRunningRequest().add(request4);
        queue.setCoreAmountAvailable(0);
        assertEquals(1500, request4.getWaitTime(), 0.00001);
    }


    /**
     * Test of nextFinishRequest of the Huge class.
     */
    @Test
    public void testNextFinishRequest(){
        reloadArray();
        Queue queue = Huge.getInstance();
        Request request = new Request(200, 40, 500, Huge.getInstance(), 1000);
        Request request2 = new Request(100, 40, 600, Huge.getInstance(), 400);
        Request request3 = new Request(400, 40, 800, Huge.getInstance(), 400);
        queue.getRunningRequest().add(request);
        queue.getRunningRequest().add(request2);
        queue.getRunningRequest().add(request3);
        assertEquals(500, queue.nextFinishRequest().getProcessingTime(), 0.001);
    }

}